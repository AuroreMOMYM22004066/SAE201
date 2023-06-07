package datahandling;

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
    public enum Order{
        // Will be use in dropdown & select order
        Ascending,
        Descending
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

    public static void main(String[] args) throws IOException {
        String Path = "C:/Users/UserName/Desktop/CSV/SisFrance_seismes_20230604151458.csv";
        //build(Path);

        Header header = Header.Choc;
        System.out.println(header.getValue());
    }

    public static void build(String path) throws IOException {
        // The path must lead to a CSV file
        // ex: "C:/Users/<user>/Desktop/monFichier.csv"

        // Build the CSV in a List<Map<String, String>>
        CSVController cc = new CSVController();
        List<Map<String, String>> map = cc.ExtractCSV(path);

        // Initialisation of assets
        Filters filtres = new Filters();
        Orders ordres = new Orders();

        //Apply filters & orders
        Print(ordres.ascendant(filtres.AtDate(map, "1995"), Header.Date));
    }

    private static void Print(List<Map<String, String>> map){
        // see what the map contains

        for (Map<String, String> alt: map) {
            System.out.println(alt.toString());
        }
        System.out.println();
    }

    private static List<Map<String, String>> SetelctFilter(Filter filtre, List<Map<String, String>> map){
        switch (filtre){
            /*
            case WithIdentifier :  return Filters.WithIdentifier(map, )
            case WithName : return
            case WithChoc : return
            case WithQIE : return
            case AtDate : return
            case BetweenDate : return
            case AtRegion : return
            case AtPointRGF : return
            case AtPointWGS : return
            */
            default: throw new IllegalArgumentException("Wrong Filter");
        }
    }
    private static List<Map<String, String>> SetelctOrder(Order ordre, List<Map<String, String>> map, Header key){
        switch (ordre){
            case Ascending: return Orders.ascendant(map, key);
            case Descending: return Orders.descendant(map, key);
            default: throw new IllegalArgumentException("Wrong Order");
        }
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
