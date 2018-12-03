package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Graphe {
    /**
     * Liste d'adjacence du Graphe
     */
    private final ListeAdjacence listeAdjacence;
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
     * Constructeur de Graphe à partir d'un fichier
     *
     * @param path chemin du fichier decrivant le graphe
     */
    public Graphe(String path) {

        this.mapColors = new CouleurSommet();
        this.listeAdjacence = new ListeAdjacence();

        boolean lectureCool = false;
        String ligne = null;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
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
            }
        } catch (IOException e) {
            System.err.println(ligne);
            e.printStackTrace();
        }
        this.nbSommets = listeAdjacence.getNbSommets();
        this.nbAretes = listeAdjacence.getNbAretes();
    }

    /**
     * Générateur de graphe aléatoire
     *
     * @param nSommets   nombre de sommmets du graphe
     * @param probablite proba qu'un sommet complete une arete
     */
    public Graphe(int nSommets, double probablite) {
        this.mapColors = new CouleurSommet();
        this.listeAdjacence = new ListeAdjacence();

        for (int i = 1; i <= nSommets; i++) {
            String sommet = "v" + i;
            listeAdjacence.addSommet(sommet);
        }
        Random r = new Random();
        listeAdjacence.getListeAdjacence().forEach((verticies, arcs) -> {
            for (String edge : listeAdjacence.getListeAdjacence().keySet())
                if (!(edge.equals(verticies)))
                    if (r.nextDouble() <= probablite) listeAdjacence.addArete(verticies, edge);
                }
        );

        listeAdjacence.getListeAdjacence().forEach((s, l) -> mapColors.addSommet(s, Couleur.BLEU));
        this.nbSommets = listeAdjacence.getNbSommets();
        this.nbAretes = listeAdjacence.getNbAretes();

    }

    public void colorateGraphe(double rougirSommets) {
        this.mapColors = new CouleurSommet();
        Random r = new Random();
        listeAdjacence.getListeAdjacence().forEach((s, l) -> mapColors.addSommet(s, (r.nextDouble() <= rougirSommets) ? Couleur.ROUGE : Couleur.BLEU));
    }

    /**
     * Affiche le graphe
     */
    public void print() {

        listeAdjacence.print();
        mapColors.print();

    }


    /**Testeur de sequence
     * @param sequence la sequence du graphe à tester
     * @return true si la sequence passée est compatible avec le graphe , false sinon
     *
     */
    public boolean isSeq2destr(Sequence2destructrice sequence) {
        System.out.println("sequence = " + sequence.toString());
        ArrayList<String> cheminParcouru = new ArrayList<>();

        for (String sommet : sequence.getListeDeSommets()) {
            if (this.listeAdjacence.getNbRougeRestantsDansListe(mapColors, cheminParcouru, sommet) > 2)
                return false;
            cheminParcouru.add(sommet);
        }
        return cheminParcouru.size() == this.getNbSommets();
    }

    /** Accesseur du nombre de sommets du graphe
     * @return
     */
    private int getNbSommets() {
        return this.listeAdjacence.getNbSommets();
    }

    /**
     * Accesseur du nombre d'arêtes du graphe
     * @return
     */
    private int getNbAretes() {
        return this.listeAdjacence.getNbAretes();
    }

    /**
     * Calcule une sequence
     * @return sequence vide si pas de sequence possible , une des sequences possible sinon
     */
    public Sequence2destructrice generateSequence() {
        if (!(isSquencePossible())) return null;
        ListeAdjacence listeAdjacenceTemp = this.listeAdjacence.copy();
        Sequence2destructrice seq = new Sequence2destructrice();

        while (seq.getNbSommets() != this.getNbSommets()) {

            String smax = getMinSommet(listeAdjacenceTemp.getMinMapSommetsRouges(mapColors, seq.getListeDeSommets()));
            seq.add(smax);
            listeAdjacenceTemp.deleteSommet(smax);
        }
        return seq;
    }


    /** Accesseur du sommet possible suivant  dans la sequence
     * @param m La map de (sommet , nombre de sommets rouges)
     * @return Le sommet ayant le moins de sommets rouges
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

    /**Testeur de sequence
     * @return true si le graphe est candidat à une sequence2destructrice false sinon
     */
    public boolean isSquencePossible() {
        if (this.nbSommets == 0)
            return false;

        if (this.nbAretes == 0)
            return true;


        if (this.mapColors.nbSommetsRouges() == 0)
            return true;

        else {
            ListeAdjacence listeAdjacenceTemp = this.listeAdjacence.copy();
            LinkedList<String> path = new LinkedList<>();
            int nsommets = this.listeAdjacence.getNbSommets();
            while (path.size() != nsommets) {


                String smax = getMinSommet(listeAdjacenceTemp.getMinMapSommetsRouges(mapColors));
                if (listeAdjacenceTemp.getNbRougeRestantsDansListe(mapColors, smax) > 3)
                    return false;
                path.add(smax);
                listeAdjacenceTemp.deleteSommet(smax);

            }
        }
        return true;
    }


}
