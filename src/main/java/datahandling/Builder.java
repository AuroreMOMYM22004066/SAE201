package datahandling;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Builder {

    public enum Header{
        Identifiant("Identifiant"),
        Date("Date (AAAA/MM/JJ)"),
        Heure("Heure"),
        Nom("Nom"),
        RegionEpicentrale("Région épicentrale"),
        Choc("Choc"),
        X_RGF93_L93("X RGF93/L93"),
        Y_RGF93_L93("Y RGF93/L93"),
        Latitude_WGS84("Latitude en WGS 84"),
        Longitude_WGS84("Longitude en WGS 84"),
        IntensiteEpicentrale("Intensité épicentrale"),
        QualiteIntensiteEpicentrale("Qualité intensité épicentrale");

        private String value;

        Header() {
            this.value = name();
        }

        Header(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static List<Map<String, String>> AllData; // without filters
    public static List<Map<String, String>> data; // with filters

    // Build the CSV in a List<Map<String, String>> and return it
    public static void build() throws IOException {
        String path = "SisFrance_seismes_20230604151458.csv";
        File f = new File("src/main/resources/CSV/" + path);

        AllData = CSVController.ExtractCSV(path);
        data = AllData;
    }
}


/*
CSV Headers :
    Identifiant
    Date (AAAA/MM/JJ)
    Heure
    Nom
    Région épicentrale
    Choc
    X RGF93/L93
    Y RGF93/L93
    Latitude en WGS 84
    Longitude en WGS 84
    Intensité épicentrale
    Qualité intensité épicentrale
*/
