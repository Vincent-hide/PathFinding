package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("===: Launch App :===");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        RootLayout rootLayout = new RootLayout();
        Scene scene = new Scene(rootLayout.create(), 640, 480);
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Path-Finding - JavaFX");
        primaryStage.show();
    }
}
