package graphics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @FXML
    private List<Map<String, String>> data;
    @FXML
    private TableView<Map<String, String>> tableView;


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

    @FXML
    // GOTO page 1
    private void chargerPage1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        VBox mapRoot = GluonMapExample.displayMap();
        controller.initializeMapContainer(mapRoot);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML
    // GOTO page 2
    private void chargerPage2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController2.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.setTableView();

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML
    // GOTO page 3
    private void chargerPage3(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("SeismeController3.fxml"));
        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }





    public void setTableView() throws IOException {
        data = datahandling.Builder.build();

        System.out.println(data.size());

        /*
        TableColumn<Map<String, String>, String> identifiantColumn = new TableColumn<>("Identifiant");
        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("Identifiant"));

        TableColumn<Map<String, String>, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        TableColumn<Map<String, String>, String> dateColumn = new TableColumn<>("Date (AAAA/MM/JJ)");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date (AAAA/MM/JJ)"));

        TableColumn<Map<String, String>, String> heureColumn = new TableColumn<>("Heure");
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("Heure"));

        TableColumn<Map<String, String>, String> chocColumn = new TableColumn<>("Choc");
        chocColumn.setCellValueFactory(new PropertyValueFactory<>("Choc"));

        TableColumn<Map<String, String>, String> regionColumn = new TableColumn<>("Région épicentrale");
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("Région épicentrale"));

        TableColumn<Map<String, String>, String> intensiteColumn = new TableColumn<>("Intensité épicentrale");
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("Intensité épicentrale"));

        TableColumn<Map<String, String>, String> qualiteColumn = new TableColumn<>("Qualité intensité épicentrale");
        qualiteColumn.setCellValueFactory(new PropertyValueFactory<>("Qualité intensité épicentrale"));

        tableView.getColumns().addAll(
                identifiantColumn, nomColumn, dateColumn, heureColumn,
                chocColumn, regionColumn, intensiteColumn, qualiteColumn
        );

        // Remplir le tableau avec les données
        for (Map<String, String> item : data) {
            tableView.getItems().add(item);
        }
        */
    }
}


/*
FXML Headers :
    <TableColumn text="Identifiant" />
    <TableColumn text="Nom" />
    <TableColumn text="Date (AAAA/MM/JJ)" />
    <TableColumn text="Heure" />
    <TableColumn text="Choc" />
    <TableColumn text="Région épicentrale" />
    <TableColumn text="Intensité épicentrale" />
    <TableColumn text="Qualité intensité épicentrale" />
*/