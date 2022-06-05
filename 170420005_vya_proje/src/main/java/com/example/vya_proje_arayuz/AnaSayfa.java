package com.example.vya_proje_arayuz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AnaSayfa {
    @FXML
    private Button ogrGiris;
    @FXML
    private Button uyeGiris;

    @FXML
    protected void giris() throws IOException {
        //Giris sayfasi yuklenir.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AnaSayfa.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 420);
        Stage stage = new Stage();
        stage.setTitle("Giris Sayfasi");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void ogrenci() throws IOException {
        //Ogrenci girisi yapildiginda yeni ogrenci nesnesi olusturulur ve giris sayfasina yonlendirilir.
        Ogrenci o = new Ogrenci();
        o.girisSayfa();
    }

    @FXML
    protected void ogrUyesi() throws IOException {
        //Ogretim elemani girisi yapildiginda yeni ogretim elemani nesnesi olusturulur ve giris sayfasina yonlendirilir.
        OgretimUyesi ouye = new OgretimUyesi();
        ouye.girisSayfa();
    }
}