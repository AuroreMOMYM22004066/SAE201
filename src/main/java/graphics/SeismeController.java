package graphics;

import com.gluonhq.maps.MapPoint;
import datahandling.Builder;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static graphics.GluonMapExample.*;

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

        setTextFieldFormatters();

        Builder.build();

        mapContainer.getChildren().add(mapRoot);
        createBindings();
    }

    /*     Set Formatter & Listener on TextFields     */
    private void setTextFieldFormatters() {

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
        });
        Min.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);

                if (value < 0 || value > 59) {
                    Min.setText(oldValue);
                }
            }

            MinutesChanger();
        });
        Sec.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);

                if (value < 0 || value > 59) {
                    Sec.setText(oldValue);
                }
            }
            SecondsChanger();
        });

        // Apply Formatter : int ∈ [0, 23]
        TextFormatter<String> hFormatter = createIntegerTextFormatter();
        H.setTextFormatter(hFormatter);

        // Apply Formatter : int ∈ [0, 59]
        TextFormatter<String> minFormatter = createIntegerTextFormatter();
        Min.setTextFormatter(minFormatter);

        // Apply Formatter : int ∈ [0, 59]
        TextFormatter<String> secFormatter = createIntegerTextFormatter();
        Sec.setTextFormatter(secFormatter);
    }
    private TextFormatter<String> createIntegerTextFormatter() {
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
    private String DepartementSelected;
    private String Lat;
    private String Lon;
    private String[] Dates = new String[2];
    private String[] Time  = new String[3];
    private String Choc;
    private String Intensity;



    /*     Set filters changes     */
    @FXML
    private TextField FilterName;
    @FXML
    private void NameChanger(){
        Name = FilterName.getText();
        if (Objects.equals(Name, "")) Name = null;
    }


    @FXML
    private ComboBox<String> comboBoxDep;
    @FXML
    private void DepartmentChanger() {
        DepartementSelected = comboBoxDep.getValue().split(" ")[0];
        if (Objects.equals(DepartementSelected, "pays")) DepartementSelected = null;
    }


    // lat
    // lon


    @FXML
    private DatePicker DateFrom;
    @FXML
    private void DateFromChanger(){
        System.out.println(Dates[0]);

        String[] dts = String.valueOf(DateFrom.getValue()).split("-");
        if (dts.length == 3){
            Dates[0] = dts[2] + "/" + dts[1] + "/" + dts[0];
        } else {
            Dates[0] = null;
        }
    }

    @FXML
    private DatePicker DateTo;
    @FXML
    private void DateToChanger(){
        String[] dts = String.valueOf(DateTo.getValue()).split("-");
        if (dts.length == 3){
            Dates[1] = dts[2] + "/" + dts[1] + "/" + dts[0];
        } else {
            Dates[1] = null;
        }
    }


    @FXML
    private TextField H;
    @FXML
    private void HourChanger(){
        Time[0] = H.getText();
    }

    @FXML
    private TextField Min;
    @FXML
    private void MinutesChanger(){
        Time[1] = Min.getText();
    }

    @FXML
    private TextField Sec;
    @FXML
    private void SecondsChanger(){
        Time[2] = Sec.getText();
    }



    @FXML
    private ComboBox<String> comboBoxChoc;
    @FXML
    private void ChocChanger() {
        Choc = comboBoxChoc.getValue();
        if (Objects.equals(Choc, "N/A")) Choc = "";
        if (Objects.equals(Choc, "TOUS")) Choc = null;
    }



    /*     Reserch button     */
    @FXML
    private void AapplyFilters() {
        // TODO : Set filters here

        UpdateData();
        removeMarkers();
        UpdateMapPoints();
    }

    private void UpdateData(){
        // TODO : Add checker for Times h is null or min is null or sec is null;
    }


    // Update Map
    private  void UpdateMapPoints() {
        mapView.flyTo(0, new MapPoint(46.227638, 2.213749), 0.1);

        GluonMapExample.addMarker(Builder.data);
    }
}