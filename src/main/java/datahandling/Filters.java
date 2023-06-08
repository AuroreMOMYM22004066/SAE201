package datahandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Filters {
    /// Contains all filters that can be applied on the actual list that represent the CSV

    public static List<Map<String, String>> WithIdentifier(List<Map<String, String>> CSV, String identifier) {
        // With a specified identifier "identifier"
        return CSV.stream()
                .filter(dico -> dico.get("Identifiant").contains(identifier))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> WithName(List<Map<String, String>> CSV, String name) {
        // With a specified name "name"
        return CSV.stream()
                .filter(dico -> dico.get("Nom").contains(name))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> AtPointRGF(List<Map<String, String>> CSV, int X, int Y, int range){
        List<Map<String, String>> data = new ArrayList<>();

        for (Map<String, String> Sis: CSV) {
            double x = Double.parseDouble(Sis.get("X RGF93/L93"));
            double y = Double.parseDouble(Sis.get("Y RGF93/L93"));
            if (X-range <= x && x <= X+range && Y-range <= y && y <= Y+range){
                if (Math.sqrt((x-X)*(x-X) + (y-Y)*(y-Y)) <= range){
                    data.add(Sis);
                }
            }
        }

        return data;
    }

    public static List<Map<String, String>> AtPointWGS(List<Map<String, String>> CSV, int X, int Y, int range){
        List<Map<String, String>> data = new ArrayList<>();

        for (Map<String, String> Sis: CSV) {
            double x = Double.parseDouble(Sis.get("Latitude en WGS 84"));
            double y = Double.parseDouble(Sis.get("Longitude en WGS 84"));
            if (X-range <= x && x <= X+range && Y-range <= y && y <= Y+range){
                if (Math.sqrt((x-X)*(x-X) + (y-Y)*(y-Y)) <= range){
                    data.add(Sis);
                }
            }
        }
        return data;
    }

    // TODO : faire le filtre avec AAAA/MM/JJ
    public static List<Map<String, String>> AtDate(List<Map<String, String>> CSV, String year) {
        // At a specified Date "year"
        return CSV.stream()
                .filter(dico -> dico.get("Date (AAAA/MM/JJ)").contains(year))
                .collect(Collectors.toList());
    }

    // TODO : faire le filtre avec AAAA/MM/JJ
    public static List<Map<String, String>> BetweenDate(List<Map<String, String>> CSV, int yearMin, int yearMax){
        // Between two specified Dates "yearMin" & "yearMax"
        if (yearMax < yearMin){ int tmp = yearMin; yearMin = yearMax; yearMax = tmp; }

        List<Map<String, String>> data = new ArrayList<>();
        for (Map<String, String> Sis: CSV) {
            int date = Integer.parseInt(Sis.get("Date (AAAA/MM/JJ)").substring(0, 4));
            if (yearMin <= date && date <= yearMax){
                data.add(Sis);
            }
        }
        return data;

    }

    public static List<Map<String, String>> AtRegion(List<Map<String, String>> CSV, String region){
        // At a specified region "region"
        return CSV.stream()
                .filter(dico -> dico.get("Région épicentrale").contains(region))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> WithQIE(List<Map<String, String>> CSV, String QIE){
        // At a specified QIE "Qualité intensité épicentrale"
        return CSV.stream()
                .filter(dico -> Objects.equals(dico.get("Qualité intensité épicentrale"), QIE))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> WithChoc(List<Map<String, String>> CSV, String Choc){
        // At a specified choc "Choc"
        return CSV.stream()
                .filter(dico -> Objects.equals(dico.get("Choc"), Choc))
                .collect(Collectors.toList());
    }

}
