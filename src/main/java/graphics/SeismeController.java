package graphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SeismeController {


    @FXML
    private VBox mapContainer;

    public void initializeMapContainer(VBox mapRoot) {
        mapContainer.getChildren().add(mapRoot);
    }
}