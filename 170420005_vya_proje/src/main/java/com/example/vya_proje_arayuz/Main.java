package com.example.vya_proje_arayuz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnaSayfa anaSayfa = new AnaSayfa();
        anaSayfa.giris();
    }

    public static void main(String[] args) {
        launch();
    }
}