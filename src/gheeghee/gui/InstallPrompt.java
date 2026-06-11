package gheeghee.gui;

import gheeghee.app.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class InstallPrompt extends Application {

    public static App targetApp;
    public static String targetAppName;

    private App app;
    private String appName;

    public InstallPrompt() {
        this.app = targetApp;
        this.appName = targetAppName;
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("installer-root");

        Label nexusLabel = new Label("NEXUS");
        nexusLabel.setId("nexus-label");

        Label confirmLabel = new Label("Would you like to install " + appName + " ?");
        confirmLabel.setId("confirm-label");

        Button installButton = new Button("Install?");
        installButton.setId("install-button");

        installButton.setOnAction(event -> {
            if (app != null) {
                primaryStage.hide();

                Thread launchThread = new Thread(() -> {
                    try {
                        app.install();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    } finally {
                        Platform.runLater(() -> {
                            System.exit(0);
                        });
                    }
                });

                launchThread.setDaemon(false);
                launchThread.start();
            }
        });

        root.getChildren().addAll(nexusLabel, confirmLabel, installButton);

        Text helper = new Text(confirmLabel.getText());
        helper.setFont(confirmLabel.getFont());

        Scene scene = new Scene(root, 450, 200);



        var css = this.getClass().getResource("/css/style.css").toExternalForm();
        if(css != null) {
            scene.getStylesheets().add(css);
        } else {
            System.out.println("CSS NOT FOUND!");
        }

        var iconResource = this.getClass().getResource("/icons/app.png");
        if (iconResource != null) {
            primaryStage.getIcons().add(new Image(iconResource.toExternalForm()));
        } else {
            System.err.println("Warning: app_icon.png not found in resources!");
        }


        primaryStage.setTitle("NEXUS App Installer");
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
