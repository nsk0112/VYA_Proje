
module com.example.vya_proje_arayuz {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;


    opens com.example.vya_proje_arayuz to javafx.fxml;
    exports com.example.vya_proje_arayuz;
}