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
    public Couleur getCouleurDeSommet(String sommet) {
        return this.couleurSommet.get(sommet);
    }

    /**
     *
     */
    public void print() {
        System.out.println("Couleurs :");
        couleurSommet.forEach((sommet, couleur) -> System.out.println("[" + sommet + "] - " + couleur));
    }
}
