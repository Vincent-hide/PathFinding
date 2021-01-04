package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(RootLayout.create(), 640, 480);
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LayoutFX");
        primaryStage.show();
    }
}
