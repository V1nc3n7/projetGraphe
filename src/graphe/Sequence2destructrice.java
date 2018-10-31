package graphe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @autor Vincent
 * @date 31/10/2018
 */

public class Sequence2destructrice {

    private List<String> listeDeSommets;

    Sequence2destructrice() {
        this.listeDeSommets = new ArrayList<>();
    }

    public Sequence2destructrice(String liste) {

        this.listeDeSommets = new ArrayList<>();
        listeDeSommets.addAll(Arrays.asList(liste.split(" ")));

    }

    public int size() {
        return listeDeSommets.size();
    }

    public boolean contains(String sommet) {
        return listeDeSommets.contains(sommet);
    }

    public String get(int index) {
        return listeDeSommets.get(index);
    }

    public String remove(int index) {
        return listeDeSommets.remove(index);
    }

    public boolean add(String s) {
        return listeDeSommets.add(s);
    }

    public int indexOf(Object o) {
        return listeDeSommets.indexOf(o);
    }


    public List<String> getListeDeSommets() {
        return listeDeSommets;
    }

}
