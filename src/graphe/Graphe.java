package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
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
        listeAdjacence.getListeAdjacence().forEach((s, l) -> {
            mapColors.addSommet(s, (r.nextDouble() <= rougirSommets) ? Couleur.ROUGE : Couleur.BLEU);
        });
    }


    /**
     *
     */
    public void print() {

        listeAdjacence.print();
        mapColors.print();

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

    /**
     * @param sequence
     * @return
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

    /**
     * @return
     */
    private int getNbSommets() {
        return this.listeAdjacence.getNbSommets();
    }

    /**
     * @return
     */
    private int getNbAretes() {
        return this.listeAdjacence.getNbAretes();
    }

    /**
     * @return null si pas de sequence possible ,à nous de verifier avant
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


    /**
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
     * @return
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

    private double repeatRandom(int nSommets, double probablite, double rougirSommets, int nb) {
        float expriences, probants;
        expriences = probants = 0;
        while (expriences != nb) {
            expriences += 1;

            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            if (graphe.isSquencePossible()) {
                probants++;
            }

        }


        return Double.parseDouble((new DecimalFormat("##.##").format((probants / expriences) * 100)));
    }


}
