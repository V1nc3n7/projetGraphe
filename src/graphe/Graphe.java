package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Graphe {
    /**
     * Nombre de sommets du Graphe
     */
    private int nbSommets;
    /**
     * nombre d'aretes du Graphe
     */
    private int nbAretes;
    /**
     * Couleur de chaque sommet
     */
    private Map<String, Couleur> couleurSommets;
    /**
     * Liste d'adjacence du Graphe
     */
    private Map<String, List<String>>listeAdjacence;


    /**
     * Constructeur de Graphe à partir d'un fichier
     *
     * @param path chemin du fichier decrivant le graphe
     */
    public Graphe(String path) {
        this.nbSommets = 0;
        this.nbAretes = 0;
        this.couleurSommets = new TreeMap<>();
        this.listeAdjacence = new TreeMap<>();

        boolean lectureCool=false;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
            String ligne;
            while ((ligne = br.readLine()) != null) {

                if (!ligne.startsWith("#")) {


                    if (ligne.startsWith("@Couleurs")) {
                        lectureCool = true;
                        ligne = br.readLine();
                    }
                    if (ligne.startsWith("@Aretes")) {
                        lectureCool = false;
                        ligne = br.readLine();
                    }
                    String[] res = ligne.split(" ");
                    String sommet = res[0];
                    if (lectureCool) {

                        String c = res[1];
                        if (c.equals("ROUGE")) {
                            addCouleur(sommet, Couleur.ROUGE);
                        } else {
                            addCouleur(sommet, Couleur.BLEU);
                        }
                    } else {
                        for (int i = 1; i < res.length; i++)
                            addArete(sommet, res[i]);
                    }


                }
                System.out.println(ligne);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nbSommets = listeAdjacence.size();
        this.nbAretes = nbAretes();
    }


    private int nbAretes() {
        int res= 0;
        for (String cle : listeAdjacence.keySet()) {
            res+=listeAdjacence.get(cle).size();

        }
        return res/2;
    }

    public Couleur getCouleurDeSommet(String sommet) {
        return this.couleurSommets.get(sommet);
    }

    private void addArete(String sommetX,String sommetY){
        if (!listeAdjacence.containsKey(sommetX)) listeAdjacence.put(sommetX,new ArrayList<>());
        listeAdjacence.get(sommetX).add(sommetY);
        if (!listeAdjacence.containsKey(sommetY)) listeAdjacence.put(sommetY,new ArrayList<>());
        listeAdjacence.get(sommetY).add(sommetX);
    }


    private void addCouleur(String sommet, Couleur c) {
        couleurSommets.put(sommet, c);
    }

    public int getNbSommets() {
        return nbSommets;
    }

    public int getNbAretes() {
        return nbAretes;
    }

    public void print() {

        listeAdjacence.forEach((sommet, liste) -> {
            System.out.println("[" + sommet + "] - " + liste.toString());
        });
        couleurSommets.forEach((sommet, couleur) -> {
            System.out.println("[" + sommet + "] - " + couleur);
        });
        System.out.println("nb de sommets " + this.nbSommets);
        System.out.println("nb arretes " + this.nbAretes);


    }
}
