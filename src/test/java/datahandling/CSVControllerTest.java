package datahandling;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVControllerTest {

    @Test
    void extractCSV() throws IOException {
        // Créez une instance de CSVController
        CSVController csvController = new CSVController();

        // Appelez la méthode extractCSV() avec les paramètres appropriés
        List<Map<String, String>> extractedValues = csvController.ExtractCSV("SisFrance_seismes_20230604151458.csv");

        assertNotNull(extractedValues);
        assertNotNull(extractedValues.get(0).keySet());

        List<String> headers = new ArrayList<>();
        headers.add("X RGF93/L93");
        headers.add("Latitude en WGS 84");
        headers.add("Longitude en WGS 84");
        headers.add("Qualité intensité épicentrale");
        headers.add("Date (AAAA/MM/JJ)");
        headers.add("Y RGF93/L93");
        headers.add("Intensité épicentrale");
        headers.add("Choc");
        headers.add("Région épicentrale");
        headers.add("Identifiant");
        headers.add("Heure");
        headers.add("Nom");

        assertEquals (extractedValues.get(0).keySet().size(), headers.size());

        for (String key : extractedValues.get(0).keySet() ) {
            assert (headers.contains(key));
        }

    }
}