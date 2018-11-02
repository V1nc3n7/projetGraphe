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
    /**
     *
     */
    private Map<String, List<String>> listeAdjacence;
    /**
     *
     */
    private int nbSommets;
    /**
     *
     */
    private int nbAretes;

    /**
     *
     */
    public ListeAdjacence() {
        this.listeAdjacence = new TreeMap<>();
        nbAretes = 0;
        nbSommets = 0;
    }

    /**
     * @param sommetX
     * @param sommetY
     */
    void addArete(String sommetX, String sommetY) {
        if (!(this.contientArete(sommetX, sommetY))) {
            if (!listeAdjacence.containsKey(sommetX)) {
                listeAdjacence.put(sommetX, new ArrayList<>());
                nbSommets++;
            }
            listeAdjacence.get(sommetX).add(sommetY);
            if (!listeAdjacence.containsKey(sommetY)) {
                listeAdjacence.put(sommetY, new ArrayList<>());
                nbSommets++;
            }
            listeAdjacence.get(sommetY).add(sommetX);
            nbAretes++;
        }


    }


    public void addSommet(String sommet) {
        if (!(this.listeAdjacence.containsKey(sommet))) {
            listeAdjacence.put(sommet, new ArrayList<>());
            nbSommets++;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean contientArete(String x, String y) {
        return (listeAdjacence.containsKey(x) &&
                listeAdjacence.containsKey(y) &&
                listeAdjacence.get(x).contains(y) &&
                listeAdjacence.get(y).contains(x));
    }

    /**
     *
     * @param sommet
     * @return
     */
    private List<String> voisinsDe(String sommet) {
        return listeAdjacence.getOrDefault(sommet, null);
    }

    public void print() {
        System.out.println("Liste d'adjacence");
        listeAdjacence.forEach((sommet, liste) -> System.out.println("[" + sommet + "] - " + liste.toString()));
        System.out.println("n = " + this.nbSommets);
        System.out.println("m = " + this.nbAretes);

    }

    /**
     *
     * @return
     */
    public ListeAdjacence copy() {
        ListeAdjacence l = new ListeAdjacence();
        this.listeAdjacence.forEach(l::add);
        return l;
    }

    /**
     *
     * @param sommet
     */
    public void deleteSommet(String sommet) {

        this.listeAdjacence.forEach((edge, liste) -> liste.remove(sommet));
        this.listeAdjacence.remove(sommet);
    }

    /**
     *
     * @param sommet
     * @param liste
     */
    private void add(String sommet, List<String> liste) {
        listeAdjacence.put(sommet, liste);
        computeNbSommets();
        computeNbAretes();

    }

    /**
     *
     * @param couleurMap
     * @param chemin
     * @param sommet
     * @return
     */
    public int getNbRougeRestantsDansListe(CouleurSommet couleurMap, List<String> chemin, String sommet) {
        int r = 0;
        for (String s : this.voisinsDe(sommet)) {
            if (!(chemin.contains(s)))
                r += ((couleurMap.sommetIsRouge(s)) ? 1 : 0);
        }
        return r;
    }

    /**
     *
     * @param couleurMap
     * @param chemin
     * @return
     */
    public Map<String, Integer> getMinMapSommetsRouges(CouleurSommet couleurMap, List<String> chemin) {
        TreeMap<String, Integer> min = new TreeMap<>();

        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(couleurMap, chemin, s)));
        return min;
    }

    /**
     *
     * @return
     */
    public Map<String, List<String>> getListeAdjacence() {
        return listeAdjacence;
    }

    /**
     *
     */
    private void computeNbSommets() {
        this.nbSommets = this.listeAdjacence.size();
    }

    /**
     *
     */
    private void computeNbAretes() {
        int i = 0;
        for (String sommet : this.listeAdjacence.keySet())
            i += this.voisinsDe(sommet).size();
        this.nbAretes = i / 2;

    }

    /**
     * @return
     */
    public int getNbSommets() {
        return nbSommets;
    }

    /**
     * @return
     */
    public int getNbAretes() {
        return nbAretes;
    }
}
