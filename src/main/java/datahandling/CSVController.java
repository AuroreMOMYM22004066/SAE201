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


    public static List<Map<String, String>> ExtractCSV(String path) throws IOException {
        // Creates the CSV
        String AbsolutePath = GetPath(path);
        OpenCsv(AbsolutePath);
        ParserHeader();
        return ParserData();
    }

    private static String GetPath(String path){
        File f = new File("src/main/resources/CSV/" + path);
        return f.getPath();
    }

    private static void OpenCsv(String path) throws IOException {
        // Get the contents of the CSV file

        Content = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        while ((line = br.readLine()) != null) {
            Content.add(line);
        }
    }

    private static List<String> ParserLine(int index){
        // Pars the given line
        List<String> line = new ArrayList<>();
        String ligne = RemoveComaInQuotes(Content.get(index));
        String[] data = ligne.split(",");

        for (String datum : data) {
            line.add(datum.replace("\"", ""));
        }

        return line;
    }

    private static void ParserHeader() {
        // Set the Header of the CSV

        Header = ParserLine(0);
    }

    private static List<Map<String, String>> ParserData() {
        // Set the DATA of the CSV

        List<Map<String, String>> Data = new ArrayList<>();

        for (int i = 1; i < Content.size(); i++){
            Map<String, String> Dico = new HashMap<>();
            List<String> line = ParserLine(i);

            for (int j = 0; j < Header.size(); j++){
                Dico.put(Header.get(j), line.get(j));
            }

            Data.add(Dico);
        }
        return Data;
    }

    private static String RemoveComaInQuotes(String line){
        boolean add = true;
        String newline = "";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\"'){
                add = !add;
            }

            if (add){
                newline += line.charAt(i);
            } else if (line.charAt(i) != ',') {
                newline += line.charAt(i);
            }
        }
        return newline;
    }
}
