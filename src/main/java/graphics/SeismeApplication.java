package graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SeismeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController.fxml"));
        Parent root = fxmlLoader.load();


        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Application Séisme");
        stage.show();

        // Get the controller instance
        SeismeController controller = fxmlLoader.getController();
        // Initialize the VBox with the map
        VBox mapRoot = GluonMapExample.displayMap();
        controller.initializeMainPage(mapRoot);
    }

    public static void main(String[] args) {
        launch();
    }
}
