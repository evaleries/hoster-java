package com.hoster.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("views/auth/auth.fxml"));
        Scene scene = new Scene(parent);
        primStage.setScene(scene);
        primStage.setMaximized(false);
        primStage.setTitle("Login - Hoster - Hotel Management");
        primStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
