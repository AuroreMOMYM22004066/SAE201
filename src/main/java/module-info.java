module com.example.sae201 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sae201 to javafx.fxml;
    exports com.example.sae201;
}