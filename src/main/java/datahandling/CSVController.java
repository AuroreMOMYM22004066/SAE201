package datahandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class CSVController {

    private List<String> Content;
    private List<String> Header;
    private List<Map<String, String>> Data;


    public List<Map<String, String>> ExtractCSV(String path) throws IOException {
        // Creates the CSV
        OpenCsv(path);
        ParserHeader();
        return ParserData();
    }

    private void OpenCsv(String path) throws IOException {
        // Get the contents of the CSV file

        Content = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        while ((line = br.readLine()) != null) {
            Content.add(line);
        }
    }

    private List<String> ParserLine(int index){
        // Pars the given line
        List<String> line = new ArrayList<>();
        String[] data = Content.get(index).split(",");

        for (String datum : data) {
            line.add(datum.replace("\"", ""));
        }

        return line;
    }

    private void ParserHeader() {
        // Set the Header of the CSV

        Header = ParserLine(0);
    }

    private List<Map<String, String>> ParserData() {
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

}
