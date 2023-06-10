package graphics;

import com.gluonhq.maps.MapPoint;

import datahandling.Builder;
import datahandling.Filters;

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
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.*;

import static graphics.GluonMapExample.*;
import static datahandling.Builder.*;


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
    private int Lat;
    private int Lon;
    private String[] Dates = new String[2];
    private String[] Time  = new String[3];
    private String Choc;
    private String Intensity;



    /*     Set filters changes     */
    @FXML private TextField FilterIdentifier;
    @FXML private TextField FilterName;
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


    /*     Charts     */



    /*     Main Page Initializer     */
    public void initialize1(VBox mapRoot) throws IOException {
        // Initialised in the Seismeapplication so it appears directly when we start the program.

        build();
        setFormattersListeners();

        // will raise an exception
        latitude.getValueFactory().setValue(null);
        longitude.getValueFactory().setValue(null);

        mapContainer.getChildren().add(mapRoot);
        createBindings();

        mapView.flyTo(0, new MapPoint(46.727638, 2.213749), 0.1);
    }


    /*     Page 2 Initializer     */
    public void initialize2(List<Map<String, String>> data) throws IOException {
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
    private void initializer3() {
        SetChart1();
        SetChart2();
        SetChart3();

        // ajout image button
        Image imgButtonMenu = new Image(SeismeController.class.getResource("buttonMenu.jpg").toString());
        ImageView viewButtonMenu = new ImageView();
        viewButtonMenu.setImage(imgButtonMenu);
        buttonMenuChoice.setGraphic(viewButtonMenu);

    }



    /*     Switch page     */
    @FXML private void chargerPage1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        VBox mapRoot = GluonMapExample.displayMap();
        controller.initialize1(mapRoot);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML private void chargerPage2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController2.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.initialize2(data);

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }
    @FXML private void chargerPage3(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeismeApplication.class.getResource("SeismeController3.fxml"));
        Parent page = fxmlLoader.load();

        SeismeController controller = fxmlLoader.getController();
        controller.initializer3();

        Scene scene = menuBar.getScene();
        scene.setRoot(page);
    }



    /*     Set Formatter & Listener on TextFields     */
    private void setFormattersListeners() {

        // Add Listener on TextField
        FilterIdentifier.textProperty().addListener((observable, oldValue, newValue) -> { IdentifierChanger(); });

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

        FilterIntensity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()){
                if (CheckIntensity(newValue)){
                    FilterIntensity.setText(newValue);
                } else {
                    FilterIntensity.setText(oldValue);
                }
            }

            IdentifierChanger();
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
    private TextFormatter<String> createFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*")) {
                return change;
            }

            return null;
        });
    }

    //------------------------------------------------------------------------------------------//
    //                                                                                          //
    //------------------------------------------------------------------------------------------//

    public void createBindings() {
        DoubleProperty doubleMacrosismique = slider.valueProperty();
        StringConverter<Number> converter = new NumberStringConverter();

        FilterIntensity.textProperty().bindBidirectional(doubleMacrosismique, converter);
    }

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


    /*    OnAction functions     */
    @FXML private void  IdentifierChanger(){
        Identifiant = FilterIdentifier.getText();
        if (Objects.equals(Identifiant, "")) Identifiant = null;
    }
    @FXML private void NameChanger(){
        Name = FilterName.getText();
        if (Objects.equals(Name, "")) Name = null;
    }
    @FXML private void RegionChanger() {
        Region = comboBoxReg.getValue();
        if (Objects.equals(Region, "TOUS")) Region = null;
    }
    private void LatitudeChanger(){
        Lat = latitude.getValue();
    }
    private void LongitudeChanger(){
        Lon = longitude.getValue();
    }
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
    @FXML private void HourChanger(){
        Time[0] = H.getText();
    }
    @FXML private void MinutesChanger(){
        Time[1] = Min.getText();
    }
    @FXML private void SecondsChanger(){
        Time[2] = Sec.getText();
    }
    @FXML private void ChocChanger() {
        Choc = comboBoxChoc.getValue();
        if (Objects.equals(Choc, "N/A")) Choc = "";
        if (Objects.equals(Choc, "TOUS")) Choc = null;
    }


    /*     Reset button     */
    @FXML private void ResetButton () {
        FilterName.setText("");

        comboBoxReg.setValue(null);

        DateFrom.setValue(null);
        DateTo.setValue(null);

        latitude.getValueFactory().setValue(null);
        longitude.getValueFactory().setValue(null);

        H.setText("");
        Min.setText("");
        Sec.setText("");

        comboBoxChoc.setValue(null);

        FilterIntensity.setText("2");
    }


    /*     Reserch button     */
    @FXML private void ResearchButton() {
        UpdateData();
        removeMarkers();
        UpdateMapPoints();
    }

    private void UpdateData(){

        data = AllData;

        if (Identifiant != null){ data = Filters.WithIdentifier(data, Identifiant); }
        if (Name != null){ data = Filters.WithName(data, Name); }
        if (Region != null) { data = Filters.AtRegion(data, Region); }
        if (Time[0] != null) { data = Filters.AtTime(data, Time); }
        if (latitude.getValue() != null && longitude.getValue() != null) { data = Filters.AtPointWGS(data, Lat, Lon, 1); }
        if (Dates[0] != null && Dates[1] != null) { data = Filters.BetweenDate(data, Dates[0], Dates[1]); }
        else if (Dates[0] != null) { data = Filters.AtDate(data, Dates[0]); }
        if (Choc != null) { data = Filters.WithChoc(data, Choc); }
        if (Intensity != null) { data = Filters.AtIntensity(data, Intensity); }
    }
    private  void UpdateMapPoints() {
        addMarker(Builder.data);
    }



    /*     Charts Page 3     */
    @FXML private PieChart pieChart1;
    @FXML private PieChart pieChart2;
    @FXML private LineChart<Number, Number> lineChart;



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

        pieChart2.setTitle("Intensité des seismes par région");
        pieChart2.setData(pieChartData);

        pieChart2.setVisible(false);

    }

    private void SetChart3(){

        int pas = 1;
        // TODO : mini combobox tout les x ans;

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
    }
}

/*
{1216=1, 1579=2, 1578=1, 1699=2, 1335=1, 1573=1, 1909=22, 1908=7, 1594=1, 1593=2, 1592=1, 1591=1, 1590=1, 1588=1,
1584=1, 1907=7, 1906=9, 1905=11, 1904=16, 1903=15, 1902=9, 1901=9, 1900=11, 1919=2, 1363=1, 1241=1, 1239=1, 1910=10,
1477=2, 1918=6, 1917=1, 1916=7, 1915=7, 1914=1, 1913=10, 1912=10, 1911=13, 1809=3, 1494=1, 1372=1, 1490=2, 1921=9,
1920=10, 1485=1, 1808=4, 1929=15, 1807=5, 1928=9, 1806=4, 1927=15, 1805=3, 1926=17, 1804=2, 1925=15, 1803=2, 1924=16,
1802=9, 1923=14, 1801=5, 1922=19, 1142=1, 1382=1, 1381=1, 1811=2, 1932=9, 1810=2, 1931=11, 1930=29, 1379=1, 1014=1,
1497=1, 1819=2, 1818=7, 1939=12, 1817=2, 1938=15, 1937=11, 1815=1, 1936=35, 1814=6, 1935=34, 1813=3, 1934=27, 1812=7,
1933=18, 2001=8, 2000=6, 1822=2, 1943=3, 1821=3, 1942=5, 1820=1, 1941=4, 1940=2, 1709=2, 1708=5, 1829=9, 1828=3, 1949=13,
1706=3, 1827=2, 1948=12, 1705=1, 1826=2, 1947=8, 1704=1, 1825=1, 1946=8, 1824=3, 1945=4, 1823=2, 1944=1, 1286=1, 1833=6,
1954=5, 1711=1, 1832=1, 1953=8, 1831=3, 1952=25, 2007=8, 1830=1, 1951=15, 2006=12, 1950=15, 2005=9, 1399=1, 2004=4, 2003=5,
1155=1, 1397=1, 2002=11, 1719=1, 1839=5, 1838=5, 1959=27, 1837=6, 1958=12, 1715=1, 1836=2, 1957=7, 1714=1, 1835=5, 1956=15,
1834=2, 1955=12, 1173=1, 1291=1, 1844=6, 1965=12, 1601=1, 1843=9, 1964=10, 1842=2, 1963=15, 1841=7, 1962=16, 1169=1, 1840=5,
1961=10, 1289=1, 1960=16, 1609=1, 1608=1, 1728=1, 1849=6, 1727=3, 1848=4, 1969=6, 1605=1, 1847=7, 1968=15, 1604=1, 1725=2,
1846=2, 1967=13, 1603=7, 1724=1, 1845=4, 1966=10, 1186=1, 1855=15, 1976=9, 1854=8, 1975=8, 1732=1, 1853=4, 1974=12, 1610=2,
1731=2, 1852=11, 1973=10, 1730=2, 1851=7, 1972=13, 1850=2, 1971=15, 1970=6, 1619=2, 1618=3, 1617=1, 1738=1, 1859=6, 1858=7,
1979=23, 1736=2, 1857=8, 1978=15, 1614=2, 1735=2, 1856=2, 1977=17, 1990=12, 1624=1, 1745=2, 1866=7, 1987=18, 1865=7, 1986=20,
1622=2, 1743=1, 1864=2, 1985=22, 1863=10, 1984=22, 1862=8, 1983=59, 1740=2, 1861=14, 1982=26, 1860=2, 1981=9, 1980=47, 1509=1,
1628=1, 1749=1, 1627=1, 1869=3, 1747=1, 1868=8, 1989=14, 1625=1, 1746=1, 1867=6, 1988=18, 1880=7, 1635=1, 1756=8, 1877=6, 1998=8,
1634=1, 1755=4, 1876=9, 1997=9, 1512=1, 1754=1, 1875=4, 1996=21, 1632=1, 1753=1, 1874=6, 1995=9, 1631=1, 1752=4, 1873=10, 1994=10,
1751=3, 1872=5, 1993=5, 1079=1, 1750=4, 1871=1, 1992=6, 1870=8, 1991=7, 1639=3, 1759=3, 1879=6, 1636=1, 1757=5, 1878=7, 1999=8,
1770=3, 1891=6, 1098=1, 1890=2, 1646=3, 1767=1, 1888=9, 1766=3, 1887=16, 1644=1, 1765=4, 1886=7, 1522=1, 1885=9, 1763=8, 1884=7,
1762=1, 1883=6, 1640=1, 1761=3, 1882=11, 1760=2, 1881=7, 1649=1, 1769=4, 1768=2, 1889=16, 1660=1, 1781=3, 1780=6, 1415=1, 1657=1,
1778=2, 1899=5, 1777=2, 1898=6, 1655=2, 1776=3, 1897=14, 1775=4, 1896=14, 1895=4, 1773=15, 1894=2, 1651=1, 1772=9, 1893=9, 1892=13,
1537=1, 1658=1, 1779=3, 1791=3, 1790=1, 1668=1, 1789=2, 1546=1, 1545=2, 1666=1, 1302=1, 1544=2, 1665=1, 1786=6, 1543=2, 1664=3,
1785=4, 1663=2, 1784=9, 1783=7, 1782=3, 1428=1, 1549=2, 1548=1, 1669=1, 1682=1, 1316=1, 1557=1, 1678=1, 1799=5, 1798=3, 1797=3,
1796=1, 1674=1, 1795=3, 1552=1, 1673=1, 1793=1, 1572=1, 1693=2, 1450=1, 1570=2, 1448=1, 1568=1, 1687=2, 1444=1, 1686=1, 1322=1,
1564=1, 1442=1, 1684=1}
 */