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


    // Create the data that contain the csv (Créé la data qui contient le fichier csv)
    public static List<Map<String, String>> ExtractCSV(String fileName) throws IOException {
        String AbsolutePath = GetPath(fileName);
        OpenCsv(AbsolutePath);
        ParserHeader();
        return ParserData();
    }


    // Get path to CSV file (Récupérer l'emplacement du fichier CSV)
    private static String GetPath(String fileName){
        File f = new File("src/main/resources/CSV/" + fileName);
        return f.getPath();
    }


    // Get the contents of the CSV file (Récupère le contenu du fichier CSV et le met dans <Content>)
    private static void OpenCsv(String path) throws IOException {
        Content = new ArrayList<>(); // Content <=> le contenu de mon fichier CSV
        BufferedReader br = new BufferedReader(new FileReader(path));  // Pour lire le fichier csv

        String line;
        while ((line = br.readLine()) != null) {
            Content.add(line);  // Lit chaque ligne du fichier CSV et l'ajoute dans la liste <Content>
        }
    }


    // Parse the given line (Sépare chaque élémetent de la ligne)
    private static List<String> ParserLine(int index){

        List<String> line = new ArrayList<>();
        String ligne = RemoveComaInQuotes(Content.get(index));
        String[] data = ligne.split(",");

        for (String datum : data) {
            line.add(datum.replace("\"", ""));
        }

        return line;
    }


    // Set the Header of the CSV (Récupère l'en-tête)
    private static void ParserHeader() {
        Header = ParserLine(0);
    }


    // Set the DATA of the CSV (Rcréé le fichier csv de manière exploitable via le code)
    private static List<Map<String, String>> ParserData() {

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


    // Remove in column coma (Enleve les virgules dans les colonens)
    private static String RemoveComaInQuotes(String line){
        boolean add = true;
        String newline = "";
        for (int i = 0; i < line.length(); i++) { // parcourir la String
            if (line.charAt(i) == '\"'){
                add = !add;                       // Quand on croise "
            }

            if (add){
                newline += line.charAt(i);       // On ajoute la lettre
            } else if (line.charAt(i) != ',') {
                newline += line.charAt(i);       // Si on a <add> a false, on est enter deux ", on ne rajoute pas les virgules
            }
        }
        return newline;
    }
}
