package gheeghee.app;

import gheeghee.gui.InstallPrompt;
import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    String[] args;

    File appFile;

    String fileName;
    String folderName = fileName;

    Path appDirectory;

    public App(String[] args) {
        this.args = args;

        appFile = new File(args[0]);
        fileName = appFile.getName();

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            folderName = fileName.substring(0, lastDotIndex);
        }

        appDirectory = Paths.get("Applications/installed_apps", folderName);
    }

    public void installOrRun() {
        if(!Files.isDirectory(appDirectory)) {
            InstallPrompt.targetApp = this;
            InstallPrompt.targetAppName = fileName;

            Application.launch(InstallPrompt.class ,args);
        } else {
            // Run
            AppLoader appL = new AppLoader();
            AppData appData = appL.loadApp(appFile.getAbsolutePath(), appDirectory.toString());

            AppRunner.runApp(appDirectory.toString(), appData);
        }
    }

    public void install() {

        try {
            Files.createDirectories(appDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }


        AppLoader appL = new AppLoader();
        AppData appData = appL.loadApp(appFile.getAbsolutePath(), appDirectory.toString());

        AppRunner.runApp(appDirectory.toString(), appData);
    }
}
