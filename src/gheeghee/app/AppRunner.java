package gheeghee.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppRunner {
    public static void runApp(String appPath, AppData appData) {

        String entryPath = appPath + "/Contents/Windows/" + appData.getEntry();

        if ("java".equals(appData.getType())) {
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-jar");
            command.add(entryPath);

            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[APP OUTPUT]" + line);
                    }
                }

                int exitCode = process.waitFor();
                System.out.println("[APP SYSTEM] Process finished with exit code: " + exitCode);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[APP SYSTEM] The application execution was interrupted.");
            }
        }
    }
}
