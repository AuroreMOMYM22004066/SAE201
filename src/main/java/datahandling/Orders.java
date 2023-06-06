package datahandling;

import java.util.*;

public class Orders {
    /// Contains all the ways you can order the CSV

    public List<Map<String, String>> ascendant(List<Map<String, String>> liste, String key){
        // Sort in ascending order

        List<Map<String, String>> newListe = new ArrayList<>(liste);

        Collections.sort(newListe, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> map1, Map<String, String> map2) {
                String identifiant1 = map1.get(key);
                String identifiant2 = map2.get(key);
                int id1 = Integer.parseInt(identifiant1);
                int id2 = Integer.parseInt(identifiant2);
                return Integer.compare(id1, id2);
            }
        });

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
}
