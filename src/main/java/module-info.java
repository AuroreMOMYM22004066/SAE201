module com.example.sae201 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.maps;


    opens graphics to javafx.fxml;
    exports graphics;
}