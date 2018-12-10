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
     * La liste d'adjacence
     */
    private Map<String, List<String>> listeAdjacence;
    /**
     * Le nombre de sommets
     */
    private int nbSommets;
    /**
     * Le nombre d'arêtes
     */
    private int nbAretes;

    /**
     * Couleur des sommets
     */
    private CouleurSommet couleurSommet;

    /**
     * Constructeur de liste d'adjacence vide
     */
    ListeAdjacence() {
        this.listeAdjacence = new TreeMap<>();
        nbAretes = 0;
        nbSommets = 0;
        this.couleurSommet = null;
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
     * Ajoute un sommet au graphe
     *
     * @param sommet le nom du sommet à ajouter
     */
    void addSommet(String sommet) {
        if (!(this.listeAdjacence.containsKey(sommet))) {
            listeAdjacence.put(sommet, new ArrayList<>());
            nbSommets++;
        }
    }

    public void setCouleurSommet(CouleurSommet couleurSommet) {
        this.couleurSommet = couleurSommet;
    }

    /**
     * Testeur d'existence d'arête
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

    /**
     * Accesseur des voisins de sommet
     *
     * @param sommet le nom du sommet à rechercher
     * @return La liste étiquetée par sommet
     */
    private List<String> voisinsDe(String sommet) {
        return listeAdjacence.get(sommet);
    }

    /**
     * Affiche la liste
     */
    void print() {
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
    ListeAdjacence copy() {
        ListeAdjacence l = new ListeAdjacence();
        this.listeAdjacence.forEach(l::add);
        return l;
    }

    /**
     * Suppresssion d'un sommet (et de ses aretes)
     *
     * @param sommet le nom du sommet à supprimer
     */
    void deleteSommet(String sommet) {

        this.listeAdjacence.forEach((edge, liste) -> liste.remove(sommet));
        this.listeAdjacence.remove(sommet);
        nbSommets--;
    }

    /**
     * Ajout de la liste complete de sommets voisins
     *
     * @param sommet le sommet en question
     * @param liste  la liste des voisons du sommet à supprimer
     */
    private void add(String sommet, List<String> liste) {
        listeAdjacence.put(sommet, liste);
        computeNbSommets();
        computeNbAretes();

    }

    /**
     * Calcul du nombre de voisins rouges encore non parcouru (dans la liste) du sommet pere
     *
     * @param chemin     la liste des sommets parcourus
     * @param sommet     Le sommet pere
     * @return le nombre de sommet rouges restant dans les voisins de sommet - les sommets présents dans chemin
     */
    int getNbRougeRestantsDansListe(List<String> chemin, String sommet) {
        int r = 0;
        for (String voisin : this.voisinsDe(sommet)) {
            if (!(chemin.contains(voisin)))
                r += ((this.couleurSommet.sommetIsRouge(voisin)) ? 1 : 0);
        }
        return r;
    }

    /**
     * Calcul du nombre de voisins rouges encore non parcouru du sommet pere
     *
     * @param sommet     Le sommet pere
     * @return Le nombre de sommet rouges restant dans les voisins de sommet
     */
    int getNbRougeRestantsDansListe(String sommet) {
        int r = 0;
        for (String s : this.voisinsDe(sommet)) {
            r += ((this.couleurSommet.sommetIsRouge(s)) ? 1 : 0);
        }
        return r;
    }


    /**
     * Calcul et genère une map de sommets rouges voisins
     *
     * @param chemin     La liste des sommets parcourus
     * @return Une map (sommet, nombre de voisins rouges de ce sommet )
     */
    Map<String, Integer> getMinMapSommetsRouges(List<String> chemin) {
        TreeMap<String, Integer> min = new TreeMap<>();

        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(chemin, s)));
        return min;
    }


    /**
     * Calcul et genère une map de sommets rouges
     *
     * @return Une map (sommet, nombre de voisins rouges)
     */
    Map<String, Integer> getMinMapSommetsRouges() {
        TreeMap<String, Integer> min = new TreeMap<>();
        this.listeAdjacence.forEach((s, l) -> min.put(s, this.getNbRougeRestantsDansListe(s)));
        return min;
    }

    /**
     * Accesseur de la liste d'adjacence
     *
     * @return La liste d'adjacence
     */
    Map<String, List<String>> getListeAdjacence() {
        return listeAdjacence;
    }

    /**
     * Calcul et affecte le nombre de sommets du graphe
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

    /**
     * Accesseur du nombre de sommets du graphe
     *
     * @return le nombre de sommets du graphe
     */
    int getNbSommets() {

        return nbSommets;
    }

    /**
     * Accesseur du nombre d'aretes du graphe
     *
     * @return le nombre d'aretes du graphe
     */
    int getNbAretes() {
        computeNbAretes();
        return nbAretes;
    }
}
