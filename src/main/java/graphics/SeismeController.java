package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class SeismeController {


    @FXML
    private VBox mapContainer;
    @FXML
    private Slider slider;
    @FXML
    private TextField textSlider;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private MenuBar menuBar;


    // Shows the map in the fxml window
    public void initializeMapContainer(VBox mapRoot) {
        // Initialised in the Seismeapplication so it appears directly when we start the program.
        mapContainer.getChildren().add(mapRoot);
        createBindings();
    }

    public void createBindings() {
        DoubleProperty doubleMacrosismique = slider.valueProperty();
        StringConverter<Number> converter = new NumberStringConverter();

        textSlider.textProperty().bindBidirectional(doubleMacrosismique, converter);
    }


    @FXML
    // Choose Department
    private void onComboBoxSelectionChanger() {
        String DepartementSelected = comboBox.getValue().split(" ")[0];
        System.out.println("Departement choisi : " + DepartementSelected);
    }


    public void changerDePage(String cheminFXML) {
        // Used to change Page
        try {
            Parent page = FXMLLoader.load(getClass().getResource(cheminFXML));
            Scene scene = menuBar.getScene();
            scene.setRoot(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    // GOTO page 1
    private void chargerPage1(ActionEvent event) {
        changerDePage("SeismeController.fxml");
    }

    @FXML
    // GOTO page 2
    private void chargerPage2(ActionEvent event) {
        changerDePage("SeismeController2.fxml");
    }

    @FXML
    // GOTO page 3
    private void chargerPage3(ActionEvent event) {
        changerDePage("SeismeController3.fxml");
    }


}