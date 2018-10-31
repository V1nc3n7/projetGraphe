package graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @autor Vincent
 * @date 31/10/2018
 */

public class ListeAdjacence {
    private Map<String, List<String>> listeAdjacence;


    public ListeAdjacence() {
        this.listeAdjacence = new TreeMap<>();
    }

    void addArete(String sommetX, String sommetY) {
        if (!listeAdjacence.containsKey(sommetX)) listeAdjacence.put(sommetX, new ArrayList<>());
        listeAdjacence.get(sommetX).add(sommetY);
        if (!listeAdjacence.containsKey(sommetY)) listeAdjacence.put(sommetY, new ArrayList<>());
        listeAdjacence.get(sommetY).add(sommetX);
    }


    int nbAretes() {
        int res = 0;
        for (String cle : listeAdjacence.keySet()) {
            res += listeAdjacence.get(cle).size();

        }
        return res / 2;
    }

    public int size() {
        return listeAdjacence.size();
    }

    public List<String> voisinsDe(String sommet) {
        return listeAdjacence.getOrDefault(sommet, null);
    }

    public void print() {
        System.out.println("Liste d'adjacence");
        listeAdjacence.forEach((sommet, liste) -> {
            System.out.println("[" + sommet + "] - " + liste.toString());
        });
    }

    public ListeAdjacence copy() {
        ListeAdjacence l = new ListeAdjacence();
        for (String s : this.listeAdjacence.keySet()) {
            l.add(s, listeAdjacence.get(s));
        }
        return l;
    }


    public void deleteSommet(String sommet) {

        this.listeAdjacence.forEach((edge, liste) -> {
            liste.remove(sommet);
        });
        this.listeAdjacence.remove(sommet);
    }

    private void add(String sommet, List<String> liste) {
        listeAdjacence.put(sommet, liste);

    }
}
