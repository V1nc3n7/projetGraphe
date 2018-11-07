package graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @autor Vincent
 * @date 31/10/2018
 */

class ListeAdjacence {
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
     * Crée une arête xy (et yx)
     *
     * @param sommetX le nom du sommet x
     * @param sommetY le nom du sommet y
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

    /**
     * @param sommet le nom du sommet à ajouter
     */
    public void addSommet(String sommet) {
        if (!(this.listeAdjacence.containsKey(sommet))) {
            listeAdjacence.put(sommet, new ArrayList<>());
            nbSommets++;
        }
    }

    /**
     * @param x le nom du sommet x
     * @param y le nom du sommet y
     * @return
     */
    private boolean contientArete(String x, String y) {
        return (listeAdjacence.containsKey(x) &&
                listeAdjacence.containsKey(y) &&
                listeAdjacence.get(x).contains(y) &&
                listeAdjacence.get(y).contains(x));
    }

    /**
     * @param sommet le nom du sommet à rechercher
     * @return
     */
    private List<String> voisinsDe(String sommet) {


        return listeAdjacence.get(sommet);
    }

    public void print() {
        System.out.println("Liste d'adjacence");
        listeAdjacence.forEach((sommet, liste) -> System.out.println("[" + sommet + "] - " + liste.toString() + " (" + liste.size() + ")"));
        System.out.println("n = " + this.nbSommets);
        System.out.println("m = " + this.nbAretes);

    }

    /**
     * Calcul de degrés
     *
     * @param sommet le nom du sommet
     * @return Le degré du sommet
     */
    public int degre(String sommet) {
        return this.voisinsDe(sommet).size();
    }

    /**
     * @return
     */
    public ListeAdjacence copy() {
        ListeAdjacence l = new ListeAdjacence();
        this.listeAdjacence.forEach(l::add);
        return l;
    }

    /**
     * @param sommet le nom du sommet à supprimer
     */
    public void deleteSommet(String sommet) {

        this.listeAdjacence.forEach((edge, liste) -> liste.remove(sommet));
        this.listeAdjacence.remove(sommet);
        nbSommets--;
    }

    /**
     * @param sommet
     * @param liste
     */
    private void add(String sommet, List<String> liste) {
        listeAdjacence.put(sommet, liste);
        computeNbSommets();
        computeNbAretes();

    }

    /**
     * @param couleurMap
     * @param chemin
     * @param sommet
     * @return
     */
    public int getNbRougeRestantsDansListe(CouleurSommet couleurMap, List<String> chemin, String sommet) {
        int r = 0;
        //System.err.println(sommet==null);
        for (String voisin : this.voisinsDe(sommet)) {
            if (!(chemin.contains(voisin)))
                r += ((couleurMap.sommetIsRouge(voisin)) ? 1 : 0);
        }
        return r;
    }


    public int getNbRougeRestantsDansListe(CouleurSommet couleurMap, String sommet) {
        int r = 0;
        for (String s : this.voisinsDe(sommet)) {
            r += ((couleurMap.sommetIsRouge(s)) ? 1 : 0);
        }
        return r;
    }

    /**
     * @param couleurMap
     * @param chemin
     * @return
     */
    public Map<String, Integer> getMinMapSommetsRouges(CouleurSommet couleurMap, List<String> chemin) {
        TreeMap<String, Integer> min = new TreeMap<>();

        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(couleurMap, chemin, s)));
        return min;
    }


    public Map<String, Integer> getMinMapSommetsRouges(CouleurSommet couleurMap) {
        TreeMap<String, Integer> min = new TreeMap<>();

        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(couleurMap, s)));
        return min;
    }

    /**
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
        computeNbAretes();
        return nbAretes;
    }
}
