package graphe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @autor Vincent
 * @date 31/10/2018
 */

public class Sequence2destructrice {
    /**
     *
     */
    private List<String> listeDeSommets;

    /**
     *
     */
    Sequence2destructrice() {
        this.listeDeSommets = new ArrayList<>();
    }

    /**
     * @param liste
     */
    public Sequence2destructrice(String liste) {

        this.listeDeSommets = new ArrayList<>();
        listeDeSommets.addAll(Arrays.asList(liste.split(" ")));

    }


    /**
     *
     * @param sommet
     * @return
     */
    public boolean contains(String sommet) {
        return listeDeSommets.contains(sommet);
    }

    /**
     *
     * @param index
     * @return
     */
    public String get(int index) {
        return listeDeSommets.get(index);
    }

    /**
     *
     * @param index
     * @return
     */
    public String remove(int index) {
        return listeDeSommets.remove(index);
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean add(String s) {
        return listeDeSommets.add(s);
    }

    /**
     *
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        return listeDeSommets.indexOf(o);
    }

    /**
     *
     * @return
     */
    public int getNbSommets() {
        return listeDeSommets.size();
    }

    /**
     *
     * @return
     */
    public List<String> getListeDeSommets() {
        return listeDeSommets;
    }

    @Override
    public String toString() {
        return listeDeSommets.toString();
    }
}
