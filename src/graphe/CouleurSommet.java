package graphe;

import java.util.Map;
import java.util.TreeMap;


class CouleurSommet {
    /**
     * La coloration
     */
    private final Map<String, Couleur> couleurSommet;

    /**
     *
     */
    public CouleurSommet() {
        this.couleurSommet = new TreeMap<>();
    }

    /**
     * Ajout d'un sommet de couleur couleur
     *
     * @param sommet  le sommet
     * @param couleur la couleur
     */
    public void addSommet(String sommet, Couleur couleur) {
        this.couleurSommet.put(sommet, couleur);
    }

    /**
     * @param sommet le sommet à interroger
     * @return true si sommet est rouge false sinon
     */
    public boolean sommetIsRouge(String sommet) {
        return this.couleurSommet.get(sommet).equals(Couleur.ROUGE);
    }


    /**
     * Accesseur du nombre de sommets rouges de la liste
     *
     * @return le nombre de sommets rouges (<= this.size)
     */
    public int nbSommetsRouges() {
        int s = 0;
        for (String sommet : couleurSommet.keySet()) if (sommetIsRouge(sommet)) s++;
        return s;
    }


    /**
     * Affiche la coloration des sommets
     */
    public void print() {
        System.out.println("Couleurs :");
        couleurSommet.forEach((sommet, couleur) -> System.out.println("[" + sommet + "] - " + couleur));
    }
}
