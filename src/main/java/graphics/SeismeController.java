package graphics;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class SeismeController {


    @FXML
    private VBox mapContainer;
    @FXML
    private Slider slider;
    @FXML
    private TextField textSlider;

    public void initializeMapContainer(VBox mapRoot) {
        mapContainer.getChildren().add(mapRoot);
        createBindings();
    }

    public void createBindings() {
        DoubleProperty doubleMacrosismique = slider.valueProperty();
        StringConverter<Number> converter = new NumberStringConverter();

        textSlider.textProperty().bindBidirectional(doubleMacrosismique, converter);
    }
}