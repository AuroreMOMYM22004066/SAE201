package datahandling;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Builder {

    public enum Filter{
        // Will be use in dropdown & select filter
        WithIdentifier,
        WithName,
        WithChoc,
        WithQIE,
        AtDate,
        BetweenDate,
        AtRegion,
        AtPointRGF,
        AtPointWGS
    }
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

    public static List<Map<String, String>> build() throws IOException {
        String path = "SisFrance_seismes_20230604151458.csv";
        File f = new File("src/main/resources/CSV/" + path);

        // Build the CSV in a List<Map<String, String>>
        List<Map<String, String>> map = CSVController.ExtractCSV(path);

        return map;
    }

    private static void Print(List<Map<String, String>> map){
        // see what the map contains

        for (Map<String, String> alt: map) {
            System.out.println(alt.toString());
        }
        System.out.println();
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
