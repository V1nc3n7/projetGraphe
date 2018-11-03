import graphe.Graphe;
import graphe.Sequence2destructrice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class MainTest {

    private static void genereTests(File f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {


            ArrayList<Integer> nsommets = new ArrayList<>();


            nsommets.add(10);
            nsommets.add(25);

            nsommets.add(50);

            nsommets.add(75);
            nsommets.add(100);
            nsommets.add(150);
            nsommets.add(200);


            ArrayList<Double> prob = new ArrayList<>();
            prob.add(0.0);
            prob.add(0.1);
            prob.add(0.2);
            prob.add(0.3);
            prob.add(0.4);
            prob.add(0.5);
            prob.add(0.6);
            prob.add(0.7);
            prob.add(0.8);
            prob.add(0.9);
            prob.add(1.0);


            ArrayList<Double> color = new ArrayList<>();
            color.add(0.0);
            color.add(0.1);
            color.add(0.2);
            color.add(0.3);
            color.add(0.4);
            color.add(0.5);
            color.add(0.6);
            color.add(0.7);
            color.add(0.8);
            color.add(0.9);
            color.add(1.0);


            bw.write("Debut : " + new Date(System.currentTimeMillis()).toString());

            bw.newLine();
            bw.write("nombre d'essais : 1000");
            bw.newLine();
            bw.write("(n,p,r)= probants %");
            bw.newLine();


            for (int n : nsommets) {
                for (Double r : color) {
                    for (Double p : prob) {
                        String e = "(" + n + "," + p + "," + r + ")=" + repeatRandom(n, p, r, 1000);
                        System.out.println(e);
                        bw.write(e);
                        bw.newLine();
                    }
                }
            }
            bw.newLine();
            bw.write("Fin : " + new Date(System.currentTimeMillis()).toString());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String repeatRandom(int nSommets, double probablite, double rougirSommets, int nb) {
        //System.out.print("("+nSommets+","+probablite+","+rougirSommets+")= ");
        float expriences, probants;
        expriences = probants = 0;
        while (expriences != nb) {
            expriences++;
            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            if (graphe.isSquencePossible()) {
                probants++;
            }

        }


        return (new DecimalFormat("##.##").format((probants / expriences) * 100) + "%");
    }

    public static void main(String... args) {

        System.out.println("Création des graphes");

        Graphe grapheA = new Graphe("res/grapheA.txt");
        System.out.println("Graphe A");
        grapheA.print();
        System.out.println();

        Graphe grapheB = new Graphe("res/grapheB.txt");
        System.out.println("Graphe B");
        grapheB.print();
        System.out.println();

        System.out.println("Création des sequences");
        Sequence2destructrice s = new Sequence2destructrice("v1 v6 v3 v8 v2 v5 v4 v7");
        Sequence2destructrice s1 = new Sequence2destructrice("v2 v6 v1 v5 v4 v3 v7 v8");
        System.out.println();


        System.out.println("Tests des séquences");

        System.out.println(grapheA.isSeq2destr(s));
        System.out.println(grapheB.isSeq2destr(s1));
        System.out.println();
        System.out.println("Tests des séquences fausses");

        System.out.println(grapheA.isSeq2destr(s1));
        System.out.println(grapheB.isSeq2destr(s));
        System.out.println();

        System.out.println("Génération d'une sequence");
        Sequence2destructrice seqt = grapheA.generateSequence();
        Sequence2destructrice seqtb = grapheB.generateSequence();
        System.out.println();
        System.out.println("Test des sequence");
        System.out.println(grapheA.isSeq2destr(seqt));
        System.out.println(grapheB.isSeq2destr(seqtb));
        System.out.println();

        System.out.println("Génération aléatoire");
        Graphe testG = new Graphe(10, 1);
        testG.print();
        System.out.println();

        testG.colorateGraphe(0.2);
        testG.print();


        String path = "log.txt";

        genereTests(new File(path));


    }

}
