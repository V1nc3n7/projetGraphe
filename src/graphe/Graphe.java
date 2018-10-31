package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    private CouleurSommet mapColors;
    /**
     * Liste d'adjacence du Graphe
     */
    private ListeAdjacence listeAdjacence;


    /**
     * Constructeur de Graphe à partir d'un fichier
     *
     * @param path chemin du fichier decrivant le graphe
     */
    public Graphe(String path) {
        this.nbSommets = 0;
        this.nbAretes = 0;
        this.mapColors = new CouleurSommet();
        this.listeAdjacence = new ListeAdjacence();

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
                            mapColors.addSommet(sommet, Couleur.ROUGE);
                        } else {
                            mapColors.addSommet(sommet, Couleur.BLEU);
                        }
                    } else {
                        for (int i = 1; i < res.length; i++)
                            listeAdjacence.addArete(sommet, res[i]);
                    }


                }
                // System.out.println(ligne);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nbSommets = this.getNbSommets();
        this.nbAretes = this.getNbAretes();
    }



    public int getNbSommets() {
        this.nbSommets = listeAdjacence.size();
        return this.nbSommets;
    }

    public int getNbAretes() {
        this.nbAretes = listeAdjacence.nbAretes();
        return this.nbAretes;
    }


    public void print() {

        listeAdjacence.print();
        mapColors.print();
        System.out.println("nb de sommets " + this.getNbSommets());
        System.out.println("nb arretes " + this.getNbAretes());


    }



    /*
        public boolean isSeq2destr(Sequence2destructrice sequence) {
            System.out.println("sequence = " + sequence.getListeDeSommets().toString());
            if (sequence.size() != this.nbSommets) {
                return false;
            } else {
                ArrayList<String> cheminParcouru = new ArrayList<>();

                //ListeAdjacence listeTemp = this.listeAdjacence.copy();
                    ListeAdjacence listeTemp = this.listeAdjacence.copy();
                for (String sommet : sequence.getListeDeSommets()) {
                    System.out.println("("+ sommet +")");
                    int sommetsRouges = 0;

                    for (String voisin : listeTemp.voisinsDe(sommet)) {
                        sommetsRouges += ((this.getCouleurDeSommet(voisin) == Couleur.ROUGE) ? 1 : 0);
                    }
                    if (sommetsRouges > 2) return false;
                    else {
                        listeTemp.deleteSommet(sommet);
                    }

                    cheminParcouru.add(sommet);
                    System.out.println("c :" + cheminParcouru.toString());
                    listeTemp.print();
                    System.out.println(listeTemp.size());
                }

                return listeTemp.size() == 0;

            }


        }
        */
    public boolean isSeq2destr(Sequence2destructrice sequence) {
        System.out.println("sequence = " + sequence.getListeDeSommets().toString());

        ArrayList<String> cheminParcouru = new ArrayList<>();

        for (String sommet : sequence.getListeDeSommets()) {

            if (this.listeAdjacence.getNbRougeRestantsDansListe(mapColors, cheminParcouru, sommet) > 2) return false;
            cheminParcouru.add(sommet);
        }
        return cheminParcouru.size() == this.getNbSommets();

    }
}
