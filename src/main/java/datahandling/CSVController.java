package datahandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class CSVController {

    private static List<String> Content;
    private static List<String> Header;
    private List<Map<String, String>> Data;


    /**
     * Controls the creation of the List of Map that represent the .csv file
     * @param fileName the name of the .csv file that we will exploit
     * @return the content of the .csv file
     */
    protected static List<Map<String, String>> ExtractCSV(String fileName) throws IOException {
        // Create the data that contain the csv (Créé la data qui contient le fichier csv)
        String AbsolutePath = GetPath(fileName);
        OpenCsv(AbsolutePath);
        ParserHeader();
        return ParserData();
    }


    /**
     * Takes the name of the CSV file and returns its path
     *
     * @param fileName the name of the CSV file
     * @return the path to go to the CSV file
     */
    private static String GetPath(String fileName){
        // Get path to CSV file (Récupérer l'emplacement du fichier CSV)
        File f = new File("src/main/resources/Data/" + fileName);
        return f.getPath();
    }


    /**
     * Takes tho path until the file and put its content into Content
     * @param path the path until the .csv file
     */
    private static void OpenCsv(String path) throws IOException {
        // Get the contents of the CSV file (Récupère le contenu du fichier CSV et le met dans <Content>)
        Content = new ArrayList<>(); // Content <=> le contenu de mon fichier CSV
        BufferedReader br = new BufferedReader(new FileReader(path));  // Pour lire le fichier csv

        String line;
        while ((line = br.readLine()) != null) {
            Content.add(line);  // Lit chaque ligne du fichier CSV et l'ajoute dans la liste <Content>
        }
    }


    /**
     * Takes the index of a line into the Content and return the elements of it separated into a List
     * @param index the number of the line
     * @return a list of the separated elements of given the line
     */
    private static List<String> ParserLine(int index){
        // Parse the given line (Sépare chaque élémetent de la ligne)
        List<String> line = new ArrayList<>();
        String ligne = RemoveCommaInQuotes(Content.get(index));
        String[] data = ligne.split(",");

        for (String datum : data) {
            line.add(datum.replace("\"", ""));
        }

        return line;
    }


    /**
     * Get and set the headers of the .csv file
     */
    private static void ParserHeader() {
        // Set the Header of the CSV (Récupère l'en-tête)
        Header = ParserLine(0);
    }


    /**
     * Recreate the .csv file from the Content and return it
     * @return a new List of Map that represent the .csv file
     */
    private static List<Map<String, String>> ParserData() {
        // Set the DATA of the CSV (Rcréé le fichier csv de manière exploitable via le code)
        List<Map<String, String>> Data = new ArrayList<>();  // Va contenir l'équivalent du fichier csv (exploitable via le code)

        for (int i = 1; i < Content.size(); i++){            // Pour chaque ligne du CSV
            Map<String, String> Dico = new HashMap<>();      // Création du dico qui va contenir la ligne
            List<String> line = ParserLine(i);               // On divise notre ligne en plusieurs éléments

            for (int j = 0; j < Header.size(); j++){         // Assigne une valeur a chaque en-tête
                Dico.put(Header.get(j), line.get(j));
            }

            Data.add(Dico);                                   // Ajoute le dictionnaire dans la Liste <data>
        }
        return Data;
    }


    /**
     * Remove all the commas that are not in a suitable place
     * @param line the line to parse
     * @return a new line without the extra commas
     */
    private static String RemoveCommaInQuotes(String line){
        // Remove in column coma (Enleve les virgules dans les colonnes)
        boolean add = true;
        String newline = "";
        for (int i = 0; i < line.length(); i++) { // parcourir la String
            if (line.charAt(i) == '\"'){
                add = !add;                       // Quand on croise <">
            }

            if (add){
                newline += line.charAt(i);       // On ajoute la lettre
            } else if (line.charAt(i) != ',') {
                newline += line.charAt(i);       // Si on a <add> a false, on est enter deux <">, on ne rajoute pas les virgules
            }
        }
        return newline;
    }
}
