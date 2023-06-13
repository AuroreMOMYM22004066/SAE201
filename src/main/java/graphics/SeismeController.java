package graphics;

import com.gluonhq.maps.MapPoint;
import datahandling.Builder;
import datahandling.Filters;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.*;

import static datahandling.Builder.*;
import static graphics.GluonMap.*;

public class SeismeController {

    /*     Menu Principal     */
    @FXML private MenuBar menuBar;

    /*     Map Page 1     */
    @FXML private VBox mapContainer;

    /*     Tableau Page 2     */
    @FXML private TableView<Map<String, String>> tableView;


    /*     Filters Values     */
    private String Identifiant;
    private String Name;
    private String Region;
    private Integer Lat = null;
    private Integer Lon = null;
    private String[] Dates = new String[2];
    private String[] Time  = new String[3];
    private String Choc;
    private String Intensity = null;



    /*     Set filters changes     */
    @FXML private AutoCompleteTextField  FilterIdentifier;
    @FXML private AutoCompleteTextField  FilterName;
    @FXML private ComboBox<String> comboBoxChoc;
    @FXML private ComboBox<String> comboBoxReg;
    @FXML private Spinner<Integer> latitude;
    @FXML private Spinner<Integer> longitude;
    @FXML private DatePicker DateFrom;
    @FXML private DatePicker DateTo;
    @FXML private TextField H;
    @FXML private TextField Min;
    @FXML private TextField Sec;
    @FXML private Slider slider;
    @FXML private TextField FilterIntensity;


    /*      element binding     */
    @FXML private Button buttonMenuChoice;


    /*     Charts Page 3     */
    @FXML private PieChart pieChart1;
    @FXML private PieChart pieChart2;
    @FXML private LineChart<Number, Number> lineChart;

    @FXML private CheckBox checkBox1;
    @FXML private CheckBox checkBox2;
    @FXML private CheckBox checkBox3;

    /* Menu Dash page 3 */
    @FXML private Pane menuDash;



    /*     Page 1 Initializer     */

    /**
     * Initialise the first page of the application
     */
    protected void initialize1(VBox mapRoot) throws IOException {
        // Initialised in the Seismeapplication so it appears directly when we start the program.

        build();
        setFormattersListeners();

        // Auto complete
        FilterIdentifier.getEntries().addAll(Filters.getAll(AllData, Header.Identifiant));
        FilterName.getEntries().addAll(Filters.getAll(AllData, Header.Nom));

        mapContainer.getChildren().add(mapRoot);
        createBindings();

        mapView.flyTo(0, new MapPoint(46.727638, 1.75), 0.1);
    }


    /*     Page 2 Initializer     */
    /**
     * Initialise the Second page of the application by initialising the board
     */
    private void initialize2(List<Map<String, String>> data) throws IOException {
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


    /*     Page 3 Initializer     */
    /**
     * Initialise the third page of the application by initialising the charts and the button menu
     */
    private void initializer3() {
        SetChart1();
        SetChart2();
        SetChart3();

        // ajout image button
        Image imgButtonMenu = new Image(SeismeController.class.getResource("buttonMenu.png").toString());
        ImageView viewButtonMenu = new ImageView();
        viewButtonMenu.setImage(imgButtonMenu);
        viewButtonMenu.setFitHeight(30);
        viewButtonMenu.setFitWidth(30);
        viewButtonMenu.setPreserveRatio(true);

        buttonMenuChoice.setGraphic(viewButtonMenu);

    }



    /*     Switch page     */

    /**
     * Loads the first page when the event is activated
     * @param event lunched when the user select the first page on the main menu
     */
    @FXML private void chargerPage1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        VBox mapRoot = GluonMap.displayMap();
        controller.initialize1(mapRoot);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    /**
     * Loads the second page when the event is activated
     * @param event lunched when the user select the second page on the main menu
     */
    @FXML private void chargerPage2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController2.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.initialize2(data);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    /**
     * Loads the third page when the event is activated
     * @param event lunched when the user select the third page on the main menu
     */
    @FXML private void chargerPage3(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController3.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.initializer3();

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }



    /*     Set Formatter & Listener on TextFields     */

    /**
     * Add listeners to some fxml functionalities and add some formatters
     * @see #createFormatter
     */
    private void setFormattersListeners() {

        // Add Listener on TextField
        FilterIdentifier.textProperty().addListener((observable, oldValue, newValue) -> { IdentifierChanger(); });

        FilterName.textProperty().addListener((observable, oldValue, newValue) -> {
            FilterName.setText(FilterName.getText().toUpperCase());
            NameChanger();
        });

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

        FilterIntensity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()){
                if (CheckIntensity(newValue)){
                    FilterIntensity.setText(newValue);
                } else {
                    FilterIntensity.setText(oldValue);
                }
            }

            IntensityChanger();
        });

        // Apply Formatter : int
        TextFormatter<String> IDFormatter = createFormatter();
        FilterIdentifier.setTextFormatter(IDFormatter);

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

    /**
     * Create an int formatter for fxml functionalities and return its
     * @return int formatter for fxml functionalities
     */
    private TextFormatter<String> createFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*")) {
                return change;
            }

            return null;
        });
    }


    /**
     * Takes a string that represent the value of the intensity and check if it is correct
     * @param value the value of the intensity
     * @return a boolean that represent if the intensity is correct
     */
    private boolean CheckIntensity(String value){
        try {
            if (value.contains("d") || value.contains("D") || value.contains("f") || value.contains("F")) {
                return false;
            }
            value = value.replace(",", ".");
            double nombre = Double.parseDouble(value);
            return nombre % 0.5 == 0;
        } catch (Exception e){
            return false;
        }
    }



    /**
     * Create a binding between a TextField and a Slider
     */
    public void createBindings() {
        DoubleProperty doubleMacrosismique = slider.valueProperty();
        StringConverter<Number> converter = new NumberStringConverter();

        FilterIntensity.textProperty().bindBidirectional(doubleMacrosismique, converter);

    }



    /* Choose graph menu on page 3 */
    /**
     * Display the selection graph menu on the page 3
     */
    public void showMenu() {
        if (menuDash.isVisible())
            menuDash.setVisible(false);
        else
            menuDash.setVisible(true);
    }


    /**
     * Handle the change graph menu on page 3 for graph 1
     */
    @FXML private void handleCheckBox1() {
        if (checkBox1.isSelected()){
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);

            pieChart1.setVisible(true);
            pieChart2.setVisible(false);
            lineChart.setVisible(false);
        } else {
            pieChart1.setVisible(false);
        }
    }
    /**
     * Handle the change graph menu on page 3 for graph 2
     */
    @FXML private void handleCheckBox2() {
        if (checkBox2.isSelected()){
            checkBox1.setSelected(false);
            checkBox3.setSelected(false);

            pieChart1.setVisible(false);
            pieChart2.setVisible(true);
            lineChart.setVisible(false);
        } else {
            pieChart2.setVisible(false);
        }
    }
    /**
     * Handle the change graph menu on page 3 for graph 3
     */
    @FXML private void handleCheckBox3() {
        if (checkBox3.isSelected()){
            checkBox1.setSelected(false);
            checkBox2.setSelected(false);

            pieChart1.setVisible(false);
            pieChart2.setVisible(false);
            lineChart.setVisible(true);
        } else {
            lineChart.setVisible(false);
        }
    }



    /*    OnAction functions     */

    /**
     * Set the value of Identifiant
     */
    @FXML private void  IdentifierChanger(){
        Identifiant = FilterIdentifier.getText();
        if (Objects.equals(Identifiant, "")) Identifiant = null;
    }
    /**
     * Set the value of Name
     */
    @FXML private void NameChanger(){
        Name = FilterName.getText();
        if (Objects.equals(Name, "")) Name = null;
    }
    /**
     * Set the value of Region
     */
    @FXML private void RegionChanger() {
        Region = comboBoxReg.getValue();
        if (Objects.equals(Region, "TOUS")) Region = null;
    }
    /**
     * Set the value of Lat
     */
    private void LatitudeChanger(){
        Lat = latitude.getValue();
    }
    /**
     * Set the value of Lon
     */
    private void LongitudeChanger(){
        Lon = longitude.getValue();
    }
    /**
     * Set the value of Dates[0]
     */
    @FXML private void DateFromChanger(){
        String dts = String.valueOf(DateFrom.getValue()).replace("-", "/");
        if (dts.split("/").length == 3){
            Dates[0] = dts;
        } else {
            Dates[0] = null;
        }
    }
    /**
     * Set the value of Dates[1]
     */
    @FXML private void DateToChanger(){
        String dts = String.valueOf(DateTo.getValue()).replace("-", "/");
        if (dts.split("/").length == 3){
            Dates[1] = dts;
        } else {
            Dates[1] = null;
        }
    }
    /**
     * Set the value of Time[0]
     */
    @FXML private void HourChanger(){
        Time[0] = H.getText();
    }
    /**
     * Set the value of Time[1]
     */
    @FXML private void MinutesChanger(){
        Time[1] = Min.getText();
    }
    /**
     * Set the value of Time[2]
     */
    @FXML private void SecondsChanger(){
        Time[2] = Sec.getText();
    }
    /**
     * Set the value of Choc
     */
    @FXML private void ChocChanger() {
        Choc = comboBoxChoc.getValue();
        if (Objects.equals(Choc, "N/A")) Choc = "";
        if (Objects.equals(Choc, "TOUS")) Choc = null;
    }
    /**
     * Set the value of Intensity
     */
    @FXML private void IntensityChanger(){
        Intensity = FilterIntensity.getText();
    }


    /*     Reset button     */

    /**
     * Reset the filters values when activated
     */
    @FXML private void ResetButton () {
        FilterName.setText("");

        comboBoxReg.setValue(null);

        DateFrom.setValue(null);
        DateTo.setValue(null);

        latitude.getValueFactory().setValue(0);
        longitude.getValueFactory().setValue(0);

        Lat = null;
        Lon = null;

        H.setText("");
        Min.setText("");
        Sec.setText("");

        comboBoxChoc.setValue(null);

        FilterIntensity.setText("2");

        removeMarkers();
        mapView.flyTo(0, new MapPoint(46.727638, 1.75), 0.1);
    }


    /*     Reserch button     */

    /**
     * Actualise the data when activated
     * @see #UpdateData()
     * @see #UpdateMapPoints()
     */
    @FXML private void ResearchButton() {
        UpdateData();
        UpdateMapPoints();
    }

    /**
     * Reset the data and update it with the filters
     * @see Filters
     */
    private void UpdateData(){

        data = AllData;

        if (Identifiant != null){ data = Filters.WithIdentifier(data, Identifiant); }
        if (Name != null){ data = Filters.WithName(data, Name); }
        if (Region != null) { data = Filters.AtRegion(data, Region); }
        if (Time[0] != null) { data = Filters.AtTime(data, Time); }
        if (Lat != null && Lon != null) { data = Filters.AtPointWGS(data, Lat, Lon, 1); }
        if (Dates[0] != null && Dates[1] != null) { data = Filters.BetweenDate(data, Dates[0], Dates[1]); }
        else if (Dates[0] != null) { data = Filters.AtDate(data, Dates[0]); }
        if (Choc != null) { data = Filters.WithChoc(data, Choc); }
        if (Intensity != null && !Intensity.equals("")) { data = Filters.AtIntensity(data, Intensity.replace("," , ".")); }
    }

    /**
     * Update the markers of the map
     * @see GluonMap#addMarker(List)
     */
    private  void UpdateMapPoints() {
        addMarker(Builder.data);
    }


    /* Set charts of page 3 */

    /**
     * Set the first chart
     */
    private void SetChart1(){
        // Intensité par région

        Map<String, Integer> regionsList = new HashMap<>();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map<String, String> map : data) {
            String regionName = map.get(Header.RegionEpicentrale.getValue());

            if (regionsList.containsKey(regionName)) {
                int num = regionsList.get(regionName);
                regionsList.put(regionName, num + 1);
            } else {
                regionsList.put(regionName, 1);
            }
        }
        for (String key : regionsList.keySet() ) {
            int value = regionsList.get(key);
            pieChartData.add(new PieChart.Data(key, value));
        }

        pieChart1.setTitle("Nombre de seismes par région");
        pieChart1.setData(pieChartData);

        pieChart1.setVisible(false);

    }

    /**
     * Set the second chart
     */
    private void SetChart2(){
        // Intensité par région

        Map<String, Integer> IntensiteList = new HashMap<>();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map<String, String> map : data) {
            String regionName = map.get(Header.IntensiteEpicentrale.getValue());

            if (IntensiteList.containsKey(regionName)) {
                int num = IntensiteList.get(regionName);
                IntensiteList.put(regionName, num + 1);
            } else {
                IntensiteList.put(regionName, 1);
            }
        }
        for (String key : IntensiteList.keySet() ) {
            int value = IntensiteList.get(key);
            pieChartData.add(new PieChart.Data(key, value));
        }

        pieChart2.setTitle("Intensité des seismes");
        pieChart2.setData(pieChartData);

        pieChart2.setVisible(false);

    }

    /**
     * Set the thirs chart
     */
    private void SetChart3(){

        int pas = 1;
        // TODO : mini combobox tout les x ans;

        // TreeMap <=> dico directement trié en fonction des clée
        Map<String, Integer> DateList = new TreeMap<>();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (Map<String, String> map : data) {
            String Date = map.get(Header.Date.getValue()).split("/")[0];

            if (DateList.containsKey(Date)) {
                int num = DateList.get(Date);
                DateList.put(Date, num + 1);
            } else {
                DateList.put(Date, 1);
            }
        }

        String minDate = (String) DateList.keySet().toArray()[0];
        String maxDate = (String) DateList.keySet().toArray()[DateList.size()-1];


        int nombre = 0;
        int pasDate = Integer.parseInt(minDate);

        for (String key : DateList.keySet() ) {

            int thisDate = Integer.parseInt(key);
            if (thisDate - pasDate < pas){
                nombre += DateList.get(key);
            } else {
                series.getData().add(new XYChart.Data<>(pasDate, nombre));
                pasDate = thisDate;
                nombre = DateList.get(key);
            }
        }


        lineChart.getData().add(series);

        lineChart.setTitle("Exemple de graphique");
        lineChart.getYAxis().setLabel("Nombre de seismes");
        lineChart.getXAxis().setLabel("années");

        // Personnalisation de l'axe des abscisses
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Integer.parseInt(minDate));
        xAxis.setUpperBound(Integer.parseInt(maxDate));
        xAxis.setTickUnit(20);

        lineChart.setVisible(false);
    }
}