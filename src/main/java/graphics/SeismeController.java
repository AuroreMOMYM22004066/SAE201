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
import java.util.*;

import static graphics.GluonMapExample.*;
import static datahandling.Builder.*;

public class SeismeController {


    @FXML
    private VBox mapContainer;

    @FXML
    private Slider slider;
    @FXML
    private TextField textSlider;



    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<Map<String, String>> tableView;


    // Initialize the main page
    public void initializeMainPage(VBox mapRoot) throws IOException {
        // Initialised in the Seismeapplication so it appears directly when we start the program.

        build();
        setFormattersListeners();

        mapContainer.getChildren().add(mapRoot);
        createBindings();

        mapView.flyTo(0, new MapPoint(46.727638, 2.213749), 0.1);
    }

    /*     Set Formatter & Listener on TextFields     */
    private void setFormattersListeners() {

        // Add Listener on TextField
        FilterName.textProperty().addListener((observable, oldValue, newValue) -> { NameChanger(); });

        H.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);

                if (value < 0 || value > 23) {
                    H.setText(oldValue);
                }
            }

            HourChanger();
        });   // limit value : [0, 23]
        Min.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);

                if (value < 0 || value > 59) {
                    Min.setText(oldValue);
                }
            }

            MinutesChanger();
        }); // limit value : [0, 59]
        Sec.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);

                if (value < 0 || value > 59) {
                    Sec.setText(oldValue);
                }
            }
            SecondsChanger();
        }); // limit value : [0, 59]

        latitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue < -90 || newValue > 90) {
                latitude.getValueFactory().setValue(oldValue);
            }

            LatitudeChanger();
        });  // limit value : [-90, 90]
        longitude.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue < -180 || newValue > 180) {
                longitude.getValueFactory().setValue(oldValue);
            }

            LongitudeChanger();
        }); // limit value : [-180, 180]

        DateFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            DateFromChanger();
        });
        DateTo.valueProperty().addListener((observable, oldValue, newValue) -> {
            DateToChanger();
        });

        // Apply Formatter : int
        TextFormatter<String> hFormatter = createFormatter();
        H.setTextFormatter(hFormatter);

        TextFormatter<String> minFormatter = createFormatter();
        Min.setTextFormatter(minFormatter);

        TextFormatter<String> secFormatter = createFormatter();
        Sec.setTextFormatter(secFormatter);

        TextFormatter<String> latFormatter = createFormatter();
        latitude.getEditor().setTextFormatter(latFormatter);

        TextFormatter<String> lonFormatter = createFormatter();
        latitude.getEditor().setTextFormatter(lonFormatter);
    }
    private TextFormatter<String> createFormatter() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*")) {
                return change;
            }

            return null;
        });
        return formatter;
    }


    public void createBindings() {
        DoubleProperty doubleMacrosismique = slider.valueProperty();
        StringConverter<Number> converter = new NumberStringConverter();

        textSlider.textProperty().bindBidirectional(doubleMacrosismique, converter);
    }


    /*     Switch page     */
    @FXML
    private void chargerPage1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        VBox mapRoot = GluonMapExample.displayMap();
        controller.initializeMainPage(mapRoot);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML
    private void chargerPage2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController2.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.setTableView(Builder.data);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML
    private void chargerPage3(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("SeismeController3.fxml"));

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }


    /*     Display data TableView     */
    public void setTableView(List<Map<String, String>> data) throws IOException {
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

        if (data != null && data.size() > 0){
            tableView.getItems().addAll(data);
        } else if (data != null) {
            Label emptyLabel = new Label("Aucun résultat, modifiez les filtres");
            tableView.setPlaceholder(emptyLabel);
        } else {
            Label emptyLabel = new Label("Aucune donnée disponible : erreur saisie données");
            tableView.setPlaceholder(emptyLabel);
        }

    }


    /*     Filters Values     */
    private String Name;
    private String Departement;
    private int Lat;
    private int Lon;
    private String[] Dates = new String[2];
    private String[] Time  = new String[3];
    private String Choc;
    private String Intensity;



    /*     Set filters changes     */
    @FXML private TextField FilterName;
    @FXML private void NameChanger(){
        Name = FilterName.getText();
        if (Objects.equals(Name, "")) Name = null;
    }


    @FXML private ComboBox<String> comboBoxDep;
    @FXML private void DepartmentChanger() {
        Departement = comboBoxDep.getValue();
        if (Objects.equals(Departement, "TOUS")) Departement = null;
    }

    @FXML private Spinner<Integer> latitude;
    @FXML private Spinner<Integer> longitude;
    private void LatitudeChanger(){
        Lat = latitude.getValue();
    }
    private void LongitudeChanger(){
        Lon = longitude.getValue();
    }


    @FXML private DatePicker DateFrom;
    @FXML private DatePicker DateTo;
    @FXML private void DateFromChanger(){
        String dts = String.valueOf(DateFrom.getValue()).replace("-", "/");
        if (dts.split("/").length == 3){
            Dates[0] = dts;
        } else {
            Dates[0] = null;
        }
    }
    @FXML private void DateToChanger(){
        String dts = String.valueOf(DateTo.getValue()).replace("-", "/");
        if (dts.split("/").length == 3){
            Dates[1] = dts;
        } else {
            Dates[1] = null;
        }
    }


    @FXML private TextField H;
    @FXML private TextField Min;
    @FXML private TextField Sec;
    @FXML private void HourChanger(){
        Time[0] = H.getText();
    }
    @FXML private void MinutesChanger(){
        Time[1] = Min.getText();
    }
    @FXML private void SecondsChanger(){
        Time[2] = Sec.getText();
    }


    @FXML private ComboBox<String> comboBoxChoc;
    @FXML private void ChocChanger() {
        Choc = comboBoxChoc.getValue();
        if (Objects.equals(Choc, "N/A")) Choc = "";
        if (Objects.equals(Choc, "TOUS")) Choc = null;
    }


    // TODO : Intensity



    /*     Reserch button     */
    @FXML private void AapplyFilters() {
        UpdateData();
        removeMarkers();
        UpdateMapPoints();
    }

    private void UpdateData(){

        data = AllData;

        // TODO : Identifier (que des chiffres donc mettre le Listenner et le Formater) && ajouter bouton.
        if (Name != null){ data = Filters.WithName(data, Name); }
        if (Departement != null) { data = Filters.AtRegion(data, Departement); }
        if (Time[0] != null) { data = Filters.AtTime(data, Time); }
        // TODO : vérifier si on recherche avec les coordonnées.
        if (Dates[0] != null && Dates[1] != null) { data = Filters.BetweenDate(data, Dates[0], Dates[1]); }
        else if (Dates[0] != null) { data = Filters.AtDate(data, Dates[0]); }
        if (Choc != null) { data = Filters.WithChoc(data, Choc); }
        // TODO : Intensity (faire le bouton et récup les valeurs)

    }


    // Update Map
    private  void UpdateMapPoints() {
        addMarker(Builder.data);
    }
}