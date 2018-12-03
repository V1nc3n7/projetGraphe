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
     *La liste d'adjacence
     */
    private Map<String, List<String>> listeAdjacence;
    /**
     *Le nombre de sommets
     */
    private int nbSommets;
    /**
     *Le nombre d'arêtes
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

    /**Ajoute un sommet au graphe
     * @param sommet le nom du sommet à ajouter
     */
    public void addSommet(String sommet) {
        if (!(this.listeAdjacence.containsKey(sommet))) {
            listeAdjacence.put(sommet, new ArrayList<>());
            nbSommets++;
        }
    }

    /**
     *
     * @param x le nom du sommet x
     * @param y le nom du sommet y
     * @return true si le graphe contient l'arete xy et yx false sinon
     */
    private boolean contientArete(String x, String y) {
        return (listeAdjacence.containsKey(x) &&
                listeAdjacence.containsKey(y) &&
                listeAdjacence.get(x).contains(y) &&
                listeAdjacence.get(y).contains(x));
    }

    /**Accesseur des voisins de sommet
     * @param sommet le nom du sommet à rechercher
     * @return La liste étiquetée par sommet
     */
    private List<String> voisinsDe(String sommet) {
        return listeAdjacence.get(sommet);
    }

    /**
     * Affiche la liste
     */
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
     * Copie la liste d'adjacence
     *
     * @return Un clone de la liste
     */
    public ListeAdjacence copy() {
        ListeAdjacence l = new ListeAdjacence();
        this.listeAdjacence.forEach(l::add);
        return l;
    }

    /**
     * Suppresssion d'un sommet (et de ses aretes)
     * @param sommet le nom du sommet à supprimer
     */
    public void deleteSommet(String sommet) {

        this.listeAdjacence.forEach((edge, liste) -> liste.remove(sommet));
        this.listeAdjacence.remove(sommet);
        nbSommets--;
    }

    /** Ajout de la liste complete de sommets voisins
     * @param sommet le sommet en question
     * @param liste la liste des voisons du sommet à supprimer
     */
    private void add(String sommet, List<String> liste) {
        listeAdjacence.put(sommet, liste);
        computeNbSommets();
        computeNbAretes();

    }

    /**
     * Calcul du nombre de voisins rouges encore non parcouru (dans la liste) du sommet pere
     * @param couleurMap La coloration des Sommet
     * @param chemin la liste des sommets parcourus
     * @param sommet Le sommet pere
     * @return le nombre de sommet rouges restant dans les voisins de sommet - les sommets présents dans chemin
     */
    public int getNbRougeRestantsDansListe(CouleurSommet couleurMap, List<String> chemin, String sommet) {
        int r = 0;
        for (String voisin : this.voisinsDe(sommet)) {
            if (!(chemin.contains(voisin)))
                r += ((couleurMap.sommetIsRouge(voisin)) ? 1 : 0);
        }
        return r;
    }

    /**
     * Calcul du nombre de voisins rouges encore non parcouru du sommet pere
     *
     * @param couleurMap La coloration des Sommet
     * @param sommet     Le sommet pere
     * @return Le nombre de sommet rouges restant dans les voisins de sommet
     */
    public int getNbRougeRestantsDansListe(CouleurSommet couleurMap, String sommet) {
        int r = 0;
        for (String s : this.voisinsDe(sommet)) {
            r += ((couleurMap.sommetIsRouge(s)) ? 1 : 0);
        }
        return r;
    }


    /**
     * Calcul et genère une map de sommets rouges voisins
     * @param couleurMap La coloration des Sommet
     * @param chemin La liste des sommets parcourus
     * @return Une map (sommet, nombre de voisins rouges de ce sommet )
     *
     */
    public Map<String, Integer> getMinMapSommetsRouges(CouleurSommet couleurMap, List<String> chemin) {
        TreeMap<String, Integer> min = new TreeMap<>();
        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(couleurMap, chemin, s)));
        return min;
    }


    /**
     * Calcul et genère une map de sommets rouges
     * @param couleurMap La coloration des Sommet
     * @return Une map (sommet, nombre de voisins rouges)
     *
     */
    public Map<String, Integer> getMinMapSommetsRouges(CouleurSommet couleurMap) {
        TreeMap<String, Integer> min = new TreeMap<>();
        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(couleurMap, s)));
        return min;
    }

    /**Accesseur de la liste d'adjacence
     * @return La liste d'adjacence
     */
    public Map<String, List<String>> getListeAdjacence() {
        return listeAdjacence;
    }

    /**
     *  Calcul et affecte le nombre de sommets du graphe
     */
    private void computeNbSommets() {
        this.nbSommets = this.listeAdjacence.size();
    }

    /**
     * Calcul et affecte le nombre d'aretes du graphe
     */
    private void computeNbAretes() {
        int i = 0;
        for (String sommet : this.listeAdjacence.keySet())
            i += this.voisinsDe(sommet).size();
        this.nbAretes = i / 2;

    }

    /**Accesseur du nombre de sommets du graphe
     * @return
     */
    public int getNbSommets() {

        return nbSommets;
    }

    /**Accesseur du nombre d'aretes du graphe
     * @return
     */
    public int getNbAretes() {
        computeNbAretes();
        return nbAretes;
    }
}
