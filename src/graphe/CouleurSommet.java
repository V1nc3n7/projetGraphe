package graphe;

import java.util.Map;
import java.util.TreeMap;

/**
 * @autor Vincent
 * @date 31/10/2018
 */

public class CouleurSommet {
    /**
     *
     */
    private final Map<String, Couleur> couleurSommet;

    /**
     *
     */
    public CouleurSommet() {
        this.couleurSommet = new TreeMap<>();
    }

    /**
     * @param sommet
     * @param couleur
     */
    public void addSommet(String sommet, Couleur couleur) {
        this.couleurSommet.put(sommet, couleur);
    }
    /**
     *
     * @param sommet
     * @return
     */
    public boolean sommetIsRouge(String sommet) {
        return this.couleurSommet.get(sommet).equals(Couleur.ROUGE);
    }

    public int nbSommetsRouges() {
        int s = 0;
        for (String sommet : couleurSommet.keySet()) if (sommetIsRouge(sommet)) s++;
        return s;
    }

    public int nbSommetsBleus() {
        return (this.couleurSommet.size() - this.nbSommetsRouges());
    }


    /**
     *
     */
    public void print() {
        System.out.println("Couleurs :");
        couleurSommet.forEach((sommet, couleur) -> System.out.println("[" + sommet + "] - " + couleur));
    }
}
