package datahandling;

import java.util.*;
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
        // With a specified name "name" (filtrer avec un nom <Name>)
        return CSV.stream()
                .filter(dico -> dico.get(Builder.Header.Nom.getValue()).contains(name))
                // pour chaque Map<String, String> dico de List<Map<String, String>> CSV, si la valeur associé a la clé "Nom" contient le <Name> que je recherche, il le met de coté
                .collect(Collectors.toList());
                // Collecte tout les Map<String, String> dico qui remplissent la condition de filtrage pour créer une nouvelle liste.


        /*
        List<Map<String, String>> newCSV = new ArrayList<>();
        foreach (dico in CSV){
            if(dico.get("Nom").contains(name)){
                newCSV.add(dico)
            }
        }

        return newCSV;
        */
    }

    public static List<Map<String, String>> AtRegion(List<Map<String, String>> CSV, String region){
        // At a specified region "region"
        return CSV.stream()
                .filter(dico -> dico.get("Région épicentrale").contains(region))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> AtPointWGS(List<Map<String, String>> CSV, int X, int Y, int range){
        List<Map<String, String>> data = new ArrayList<>();

        for (Map<String, String> Sis: CSV) {

            if (Sis.get(Builder.Header.Latitude_WGS84.getValue()) != "" && Sis.get(Builder.Header.Longitude_WGS84.getValue()) != "")
            {
                double x = Double.parseDouble(Sis.get(Builder.Header.Latitude_WGS84.getValue()));
                double y = Double.parseDouble(Sis.get(Builder.Header.Longitude_WGS84.getValue()));
                if (X-range <= x && x <= X+range && Y-range <= y && y <= Y+range){
                    if (Math.sqrt((x-X)*(x-X) + (y-Y)*(y-Y)) <= range){
                        data.add(Sis);
                    }
                }
            }
        }
        return data;
    }

    public static List<Map<String, String>> AtDate(List<Map<String, String>> CSV, String year) {
        // At a specified Date "year"
        return CSV.stream()
                .filter(dico -> Objects.equals(dico.get("Date (AAAA/MM/JJ)"), year))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> BetweenDate(List<Map<String, String>> CSV, String yearMin, String yearMax){
        // Between two specified Dates "yearMin" & "yearMax"
        int[] Min = SplitDate(yearMin);
        int[] Max = SplitDate(yearMax);

        List<Map<String, String>> data = new ArrayList<>();

        for (Map<String, String> Sis: CSV) {
            if (BtwDate(Min, Max, Sis.get(Builder.Header.Date.getValue()))){
                data.add(Sis);
            }
        }

        return data;

    }

    public static List<Map<String, String>> AtTime(List<Map<String, String>> CSV, String[] Time){

        if (Time[0] == null) { return CSV; }
        if (Time[1] == null ) { Time[2] = null; }

        List<Map<String, String>> data = new ArrayList<>();

        for (Map<String, String> Sis: CSV) {
            if (equalsTime(Time, Sis.get(Builder.Header.Heure.getValue()))){
                data.add(Sis);
            }
        }
        return data;
    }

    public static List<Map<String, String>> WithChoc(List<Map<String, String>> CSV, String Choc){
        // At a specified choc "Choc"
        return CSV.stream()
                .filter(dico -> Objects.equals(dico.get("Choc"), Choc))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> AtIntensity (List<Map<String, String>> CSV, String intensity) {
        // At a specified intensity "intensité épicentrale"
        return CSV.stream()
                .filter(dico -> Objects.equals(dico.get(Builder.Header.IntensiteEpicentrale.getValue()), intensity))
                .collect(Collectors.toList());
    }




    private static boolean BtwDate(int[] Min, int[] Max, String year){
        int[] actual = SplitDate(year);

        for (int i = 0; i < actual.length; i++){
            if (Min[i] <= actual[i] && actual[i] < Max[i]){
                return true;
            }
        }

        return false;
    }
    private static int[] SplitDate(String v1){
        String[] p1 = v1.split("/");
        int[] p2 = new int[p1.length];
        for (int i = 0; i< p1.length; i++){
            p2[i] = Integer.parseInt(p1[i]);
        }
        return p2;
    }

    private static boolean equalsTime(String[] Time, String dataTime){

        if (Objects.equals(dataTime, "") || Objects.equals(dataTime, null)){ return false; }

        String[] cpTime = SplitTime(dataTime);

        int min = Math.min(Time.length, cpTime.length);

        for (int i = 0; i < min; i++) {
            if (Time[i] != null && Integer.parseInt(Time[i]) != Integer.parseInt(cpTime[i])) {
                return false;
            }
        }
        return true;
    }
    private static String[] SplitTime(String v1){

        String[] p1 = v1.split(" ");

        String[] p2 = new String[p1.length / 2];
        for (int i = 0; i < p1.length; i += 2) {
            String value = p1[i];
            p2[i / 2] = value;
        }

        return p2;
    }
}
