package gheeghee.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtility {

    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                File newFile = new File(filePath);

                if (!newFile.getCanonicalPath().startsWith(destDir.getCanonicalPath() + File.separator)) {
                    throw new IOException("Malicious zip entry detected outside destination: " + entry.getName());
                }

                if (!entry.isDirectory()) {
                    new File(newFile.getParent()).mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = zipIn.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                } else {
                    newFile.mkdirs();
                }

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }
}
