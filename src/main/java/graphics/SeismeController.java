package graphics;

import com.gluonhq.maps.MapPoint;
import datahandling.Builder;
import datahandling.Filters;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.ArrayList;
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
    private List<Map<String, String>> AllData; // without filters
    @FXML
    private List<Map<String, String>> data; // with filters
    @FXML
    private TableView<Map<String, String>> tableView;

    @FXML
    private String DepartementSelected;

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

    // Display the data in the TableView
    public void setTableView() throws IOException {
        // Display the content of the data in the TableView

        TableColumn<Map<String, String>, String> identifiantColumn = new TableColumn<>("Identifiant");
        identifiantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Identifiant")));

        TableColumn<Map<String, String>, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Nom")));

        TableColumn<Map<String, String>, String> dateColumn = new TableColumn<>("Date (AAAA/MM/JJ)");
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Date (AAAA/MM/JJ)")));

        TableColumn<Map<String, String>, String> heureColumn = new TableColumn<>("Heure");
        heureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Heure")));

        TableColumn<Map<String, String>, String> chocColumn = new TableColumn<>("Choc");
        chocColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Choc")));

        TableColumn<Map<String, String>, String> regionColumn = new TableColumn<>("Région épicentrale");
        regionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Région épicentrale")));

        TableColumn<Map<String, String>, String> intensiteColumn = new TableColumn<>("Intensité épicentrale");
        intensiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Intensité épicentrale")));

        TableColumn<Map<String, String>, String> qualiteColumn = new TableColumn<>("Qualité intensité épicentrale");
        qualiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Qualité intensité épicentrale")));

        tableView.getColumns().addAll(
                identifiantColumn, nomColumn, dateColumn, heureColumn,
                chocColumn, regionColumn, intensiteColumn, qualiteColumn
        );

        if (data != null && data.size() != 0){
            tableView.getItems().addAll(data);
        } else if (data != null) {
            Label emptyLabel = new Label("Aucun résultat, modifiez les filtres");
            tableView.setPlaceholder(emptyLabel);
        } else {
            Label emptyLabel = new Label("Aucune donnée disponible : erreur saisie données");
            tableView.setPlaceholder(emptyLabel);
        }

    }

    private  void UpdateMapPoints() {
        MapPoint mapPoint = new MapPoint(46.227638, 2.213749);
        GluonMapExample.addMarker(mapPoint);
    }


    @FXML
    private void AapplyFilters() throws IOException {
        // TODO : Set filters here

        AllData = Builder.build();
        data = Filters.AtDate(AllData, "1996");

        UpdateMapPoints();
    }
}