package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

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
    private final CouleurSommet mapColors;
    /**
     * Liste d'adjacence du Graphe
     */
    private final ListeAdjacence listeAdjacence;


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

        boolean lectureCool = false;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
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


    private int getNbSommets() {
        this.nbSommets = listeAdjacence.size();
        return this.nbSommets;
    }

    private int getNbAretes() {
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
            if (this.listeAdjacence.getNbRougeRestantsDansListe(mapColors, cheminParcouru, sommet) > 2)
                return false;
            cheminParcouru.add(sommet);
        }
        return cheminParcouru.size() == this.getNbSommets();
    }

    /**
     * @return null si pas de sequence possible ,à nous de verifier avant
     */
    public Sequence2destructrice generateSequence() {
        ListeAdjacence listeAdjacenceTemp = this.listeAdjacence.copy();
        Sequence2destructrice seq = new Sequence2destructrice();

        while (seq.getNbSommets() != this.getNbSommets()) {

            String smax = getMinSommet(listeAdjacenceTemp.getMinMapSommetsRouges(mapColors, seq.getListeDeSommets()));
            seq.add(smax);
            listeAdjacenceTemp.deleteSommet(smax);
            // System.out.println(seq.getListeDeSommets().toString());
        }
        return seq;
    }


    /**
     *
     * @param m
     * @return
     */
    private String getMinSommet(Map<String, Integer> m) {

        String smax = null;
        int maxou = Integer.MAX_VALUE;
        for (String s : m.keySet()) {
            if (maxou > m.get(s)) {
                maxou = m.get(s);
                smax = s;
            }
        }
        return smax;

    }

    /**
     *
     * @return
     */
    private boolean isSquencePossible() {
        if (getNbSommets() == 0)
            return false;
        else {
            ListeAdjacence listeAdjacenceTemp = this.listeAdjacence.copy();
            LinkedList<String> path = new LinkedList<>();

            while (path.size() != this.getNbSommets()) {


                String smax = getMinSommet(listeAdjacenceTemp.getMinMapSommetsRouges(mapColors, path));
                if (listeAdjacenceTemp.getNbRougeRestantsDansListe(mapColors, path, smax) > 3)
                    return false;
                path.add(smax);
                listeAdjacenceTemp.deleteSommet(smax);

            }
        }
        return true;
    }


}
