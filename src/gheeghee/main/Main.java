package gheeghee.main;

import gheeghee.app.AppData;
import gheeghee.app.AppLoader;
import gheeghee.app.AppRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Provide .app file");
            return;
        }

        File appFile = new File(args[0]);

        String fileName = appFile.getName();
        String folderName = fileName;

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            folderName = fileName.substring(0, lastDotIndex);
        }

        Path appDirectory = Paths.get("Applications/installed_apps", folderName);
        if(!Files.isDirectory(appDirectory)) {
            try {
                Files.createDirectories(appDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AppLoader appL = new AppLoader();
        AppData appData = appL.loadApp(appFile.getAbsolutePath(), appDirectory.toString());

        AppRunner.runApp(appDirectory.toString(), appData);
    }
}
