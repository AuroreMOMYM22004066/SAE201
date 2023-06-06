module com.example.sae201 {
    requires javafx.controls;
    requires javafx.fxml;


    opens graphics to javafx.fxml;
    exports graphics;
}