package graphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SeismeController {

    @FXML
    private Button openMapButton;

    @FXML
    private void openMapButtonClicked() {
        GluonMapExample.displayMap();
    }
}