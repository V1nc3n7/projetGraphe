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
     * La sequence
     */
    private final List<String> listeDeSommets;

    /**
     * Constructeur
     */
    Sequence2destructrice() {
        this.listeDeSommets = new ArrayList<>();
    }

    /**
     * Constructeur non vide
     *
     * @param liste la liste des sommets
     */
    public Sequence2destructrice(String liste) {

        this.listeDeSommets = new ArrayList<>();
        listeDeSommets.addAll(Arrays.asList(liste.split(" ")));

    }


    /**
     * @param sommet
     * @return true si la sequence contient le sommet false sinon
     */
    public boolean contains(String sommet) {
        return listeDeSommets.contains(sommet);
    }

    /**
     * Accesseur indexé
     *
     * @param index l'index de la sequence
     * @return le sommet a la i-eme position
     */
    public String get(int index) {
        return listeDeSommets.get(index);
    }

    /**
     * Suppresseur indexé
     *
     * @param index l'index de la sequence
     * @return le sommet a la i-eme position apres etre supprimé
     */
    public String remove(int index) {
        return listeDeSommets.remove(index);
    }

    /**
     * Ajoue d'un sommet
     *
     * @param s
     * @return
     */
    public void add(String s) {
        listeDeSommets.add(s);
    }

    /**
     * @param s le sommet
     * @return l'indice de s
     */
    public int indexOf(String s) {
        return listeDeSommets.indexOf(s);
    }

    /**
     * @return La taille de la liste
     */
    public int getNbSommets() {
        return listeDeSommets.size();
    }

    /**
     * @return la liste de sommets
     */
    public List<String> getListeDeSommets() {
        return listeDeSommets;
    }

    @Override
    public String toString() {
        return listeDeSommets.toString();
    }
}
