package datahandling;

import java.util.*;

public class Orders {
    /// Contains all the ways you can order the CSV

    public List<Map<String, String>> ascendant(List<Map<String, String>> liste, String key){
        // Sort in ascending order

        List<Map<String, String>> newListe = new ArrayList<>(liste);

        for (int i = 0; i < newListe.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < newListe.size(); j++)
            {
                //if (newListe.get(j) < newListe.get(index))
                if (Comparator(newListe.get(j).get(key), newListe.get(index).get(key) ,key)){
                    index = j;
                }
            }

            Map<String, String> min = newListe.get(index);
            newListe.set(index, newListe.get(i));
            newListe.set(i, min);
        }

        return newListe;
    }
    public List<Map<String, String>> descendant(List<Map<String, String>> liste, String key){
        // Sort in descending order

        List<Map<String, String>> newListe = new ArrayList<>(liste);

        Collections.sort(newListe, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> map1, Map<String, String> map2) {
                String identifiant1 = map1.get(key);
                String identifiant2 = map2.get(key);
                int id1 = Integer.parseInt(identifiant1);
                int id2 = Integer.parseInt(identifiant2);
                return Integer.compare(id2, id1);
            }
        });

        return newListe;
    }

    private boolean Comparator(String v1, String v2, String key){
        // return true if v1 < v2
        switch (key){
            case "Identifiant": return CompInt(v1, v2); // int

            case "Date (AAAA/MM/JJ)": return CompDate(v1, v2); // int split "/"

            case "Région épicentrale": case "Nom": case "Choc": case "Qualité intensité épicentrale": return CompAlpha(v1, v2); // Alphabetique

            case "Latitude en WGS 84": case "Longitude en WGS 84": case "X RGF93/L93": case "Y RGF93/L93": case "Intensité épicentrale": return CompDbl(v1, v2);// double

            case "Heure": return CompTime(v1, v2); // int

            default: return false;
        }
    }

    private boolean CompInt(String v1, String v2){
        // return true if (int)v1 < (int)v2
        return Integer.parseInt(v1) < Integer.parseInt(v2);
    }
    private boolean CompDbl(String v1, String v2){
        // return true if (double)v1 < (double)v2
        return Double.parseDouble(v1) < Double.parseDouble(v2);
    }
    private boolean CompDate(String v1, String v2){
        // return true if (Date)v1 < (Date)v2
        // Date format (AAAA/MM/JJ)

        String[] s1 = v1.split("/");
        String[] s2 = v2.split("/");

        int min = Math.min(s1.length, s2.length);
        switch (min){
            case 3: return !Objects.equals(s1[0], s2[0]) ? CompInt(s1[0], s2[0]) : !Objects.equals(s1[1], s2[1]) ? CompInt(s1[1], s2[1]) : !Objects.equals(s1[2], s2[2]) ? CompInt(s1[2], s2[2]) : false;
            case 2: return !Objects.equals(s1[0], s2[0]) ? CompInt(s1[0], s2[0]) : !Objects.equals(s1[1], s2[1]) ? CompInt(s1[1], s2[1]) : false;
            case 1: return !Objects.equals(s1[0], s2[0]) ? CompInt(s1[0], s2[0]) : false;
            default: return false;
        }
    }
    private boolean CompAlpha(String v1, String v2){
        // return true if (String)v1 < (String)v2
        return v1.compareTo(v2) < 0;
    }
    private boolean CompTime(String v1, String v2){
        // return true if (Time)v1 < (Time)v2

        int[] p1 = Objects.equals(v1, "") ? new int[0] : SplitTime(v1);
        int[] p2 = Objects.equals(v2, "") ? new int[0] : SplitTime(v2);

        if (p2.length == 0){
            return false;
        }

        if (p1.length == 0){
            return true;
        }

        int min = Math.min(p1.length, p2.length);
        switch (min){
            case 3: return !Objects.equals(p1[0], p2[0]) ? p1[0] < p2[0] : !Objects.equals(p1[1], p1[1]) ? p1[1] < p2[1] : !Objects.equals(p1[2], p2[2]) ? p1[2] < p2[2] : false;
            case 2: return !Objects.equals(p1[0], p2[0]) ? p1[0] < p2[0] : !Objects.equals(p1[1], p1[1]) ? p1[1] < p2[1] : false;
            case 1: return !Objects.equals(p1[0], p2[0]) ? p1[0] < p2[0] : false;
            case 0: return p1.length == 0;
            default: return false;
        }
    }

    private int[] SplitTime(String v1){
        String[] p1 = v1.split("\\s+");

        // Convertit chaque partie en entier et les ajoute au tableau
        int[] durationArray = new int[p1.length / 2];
        for (int i = 0; i < p1.length; i += 2) {
            int value = Integer.parseInt(p1[i]);
            durationArray[i / 2] = value;
        }

        return durationArray;
    }
}

