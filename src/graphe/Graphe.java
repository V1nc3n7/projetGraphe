package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Graphe {
    private int nbSommets;
    private int nbAretes;
    private Map<String,Couleur> couleur;
    private Map<String, List<String>>listeAdjacence;

    public Graphe(String path) {
        this.nbSommets = 0;
        this.nbAretes = 0;
        this.couleur = new HashMap<>();
        this.listeAdjacence = new HashMap<>();

        boolean lectureCool=false;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (!ligne.startsWith("#")) {


                    if (ligne.startsWith("@Couleurs")) lectureCool = true;
                    if (ligne.startsWith("@Aretes")) lectureCool = false;
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private int nbSommets() {
        return listeAdjacence.size();
    }
    private int nbAretes() {
        int res= 0;
        for (String cle : listeAdjacence.keySet()) {
            res+=listeAdjacence.get(cle).size();

        }
        return res;
    }


    private void addArete(String sommetX,String sommetY){
        if (!listeAdjacence.containsKey(sommetX)) listeAdjacence.put(sommetX,new ArrayList<>());
        listeAdjacence.get(sommetX).add(sommetY);
        if (!listeAdjacence.containsKey(sommetY)) listeAdjacence.put(sommetY,new ArrayList<>());
        listeAdjacence.get(sommetY).add(sommetX);
    }



    public void addCouleur(String sommet,Couleur c) {
        couleur.put(sommet, c);
    }
}
