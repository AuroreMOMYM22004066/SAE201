package datahandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Builder {

    public static void main(String[] args) throws IOException {
        String Path = "C:/Users/Bastien/Desktop/CSV/SisFrance_seismes_20230604151458.csv";
        build(Path);
    }

    public static void build(String path) throws IOException {
        // The path must lead to a CSV file
        // ex: "C:/Users/UsersName/Desktop/monFichier.csv"

        // Build the CSV in a List<Map<String, String>>
        CSVController cc = new CSVController();
        List<Map<String, String>> map = cc.ExtractCSV(path);

        // Initialisation of assets
        Filters filtres = new Filters();
        Orders ordres = new Orders();

        //Apply filters & orders
        Print(filtres.AtDate(map, 1995));
        //Print(ordres.ascendant(filtres.AtDate(map, 1995), "Date (AAAA/MM/JJ)"));
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
