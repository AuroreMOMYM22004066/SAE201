package datahandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {

    private List<Map<String, String>> listeDictionnaires;

    @BeforeEach
    void setUp() throws IOException {

        listeDictionnaires = new ArrayList<>();

        // Créer le premier dictionnaire avec les valeurs
        Map<String, String> dictionnaire1 = new HashMap<String, String>() {{
            put(Builder.Header.Identifiant.getValue(), "120009");
            put(Builder.Header.Date.getValue(), "1807/03/23");
            put(Builder.Header.Heure.getValue(), "11 h 25 min");
            put(Builder.Header.Nom.getValue(), "CHATAIGNERAIE");
            put(Builder.Header.RegionEpicentrale.getValue(), "QUERCY-ROUERGUE");
            put(Builder.Header.Choc.getValue(), "");
            put(Builder.Header.X_RGF93_L93.getValue(), "658972.32");
            put(Builder.Header.Y_RGF93_L93.getValue(), "6385421.78");
            put(Builder.Header.Latitude_WGS84.getValue(), "44.57");
            put(Builder.Header.Longitude_WGS84.getValue(), "2.48");
            put(Builder.Header.IntensiteEpicentrale.getValue(), "6");
            put(Builder.Header.QualiteIntensiteEpicentrale.getValue(), "ASSEZ SURE");
        }};
        Map<String, String> dictionnaire2 = new HashMap<String, String>() {{
            put(Builder.Header.Identifiant.getValue(), "120009");
            put(Builder.Header.Date.getValue(), "1939/05/16");
            put(Builder.Header.Heure.getValue(), "4 h 5 min");
            put(Builder.Header.Nom.getValue(), "VALLEE DE L'AVEYRON");
            put(Builder.Header.RegionEpicentrale.getValue(), "QUERCY-ROUERGUE");
            put(Builder.Header.Choc.getValue(), "");
            put(Builder.Header.X_RGF93_L93.getValue(), "707969.03");
            put(Builder.Header.Y_RGF93_L93.getValue(), "6363075.37");
            put(Builder.Header.Latitude_WGS84.getValue(), "44.37");
            put(Builder.Header.Longitude_WGS84.getValue(), "3.10");
            put(Builder.Header.IntensiteEpicentrale.getValue(), "6");
            put(Builder.Header.QualiteIntensiteEpicentrale.getValue(), "ASSEZ SURE");
        }};
        Map<String, String> dictionnaire3 = new HashMap<String, String>() {{
            put(Builder.Header.Identifiant.getValue(), "640536");
            put(Builder.Header.Date.getValue(), "1897/05/20");
            put(Builder.Header.Heure.getValue(), "4 h 5 min");
            put(Builder.Header.Nom.getValue(), "BEARN");
            put(Builder.Header.RegionEpicentrale.getValue(), "PYRENEES OCCIDENTALES");
            put(Builder.Header.Choc.getValue(), "");
            put(Builder.Header.X_RGF93_L93.getValue(), "405527.13");
            put(Builder.Header.Y_RGF93_L93.getValue(), "6230900.45");
            put(Builder.Header.Latitude_WGS84.getValue(), "43.12");
            put(Builder.Header.Longitude_WGS84.getValue(), "-0.62");
            put(Builder.Header.IntensiteEpicentrale.getValue(), "4.5");
            put(Builder.Header.QualiteIntensiteEpicentrale.getValue(), "INFORMATION ISOLÉE");
        }};

        listeDictionnaires.add(dictionnaire1);
        listeDictionnaires.add(dictionnaire2);
        listeDictionnaires.add(dictionnaire3);

    }


    @Test
    void withIdentifier() {
        List<Map<String, String>> data1 = Filters.WithIdentifier(listeDictionnaires, "123456");
        List<Map<String, String>> data2 = Filters.WithIdentifier(listeDictionnaires, "640536");

        assertEquals(data1, new ArrayList<>());
        assertEquals(data2.size(), 1);
    }

    @Test
    void withName() {
        List<Map<String, String>> data1 = Filters.WithName(listeDictionnaires, "");
        List<Map<String, String>> data2 = Filters.WithName(listeDictionnaires, "BEA");
        List<Map<String, String>> data3 = Filters.WithName(listeDictionnaires, "RN");

        assertEquals(data1.size(), 3);
        assertEquals(data2.size(), 1);
        assertEquals(data3.size(), 0);
    }

    @Test
    void atRegion() {
        List<Map<String, String>> data1 = Filters.AtRegion(listeDictionnaires, "QUERCY-ROUERGUE");
        List<Map<String, String>> data2 = Filters.AtRegion(listeDictionnaires, "");
        List<Map<String, String>> data3 = Filters.AtRegion(listeDictionnaires, "RN");

        assertEquals(data1.size(), 2);
        assertEquals(data2.size(), 0);
        assertEquals(data3.size(), 0);
    }

    @Test
    void atPointWGS() {
        List<Map<String, String>> data1 = Filters.AtPointWGS(listeDictionnaires, 43, 0, 1);
        List<Map<String, String>> data2 = Filters.AtPointWGS(listeDictionnaires, 44, 3, 1);
        List<Map<String, String>> data3 = Filters.AtPointWGS(listeDictionnaires, 44, 1, 1);

        assertEquals(data1.size(), 1);
        assertEquals(data2.size(), 2);
        assertEquals(data3.size(), 0);
    }

    @Test
    void atDate() {
    }

    @Test
    void betweenDate() {
    }

    @Test
    void atTime() {
    }

    @Test
    void withChoc() {
    }

    @Test
    void atIntensity() {
        List<Map<String, String>> data1 = Filters.AtIntensity(listeDictionnaires, "4.5");
        List<Map<String, String>> data2 = Filters.AtIntensity(listeDictionnaires, "6");
        List<Map<String, String>> data3 = Filters.AtIntensity(listeDictionnaires, "7.5");

        assertEquals(data1.size(), 1);
        assertEquals(data2.size(), 2);
        assertEquals(data3.size(), 0);
    }

    @Test
    void getAll() {
        List<String> data1 = Filters.getAll(listeDictionnaires, Builder.Header.Latitude_WGS84);
        List<String> data2 = Filters.getAll(listeDictionnaires, Builder.Header.Nom);

        assertEquals(data1.size(), listeDictionnaires.size());
        assertEquals(data2.size(), listeDictionnaires.size());
    }
}