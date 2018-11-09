/**
 * @autor Vincent
 * @date 08/11/2018
 */

import graphe.Graphe;
import graphe.Sequence2destructrice;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class MainTest {
    private static double compareLogs(File f1, File f2) {
        try (BufferedReader br1 = new BufferedReader(new FileReader(f1));
             BufferedReader br2 = new BufferedReader(new FileReader(f2))
        ) {
            ArrayList<Double> d = new ArrayList<>();
            String l1, l2;
            l1 = null;
            l2 = null;
            while ((l1 = br1.readLine()) != null && (l2 = br2.readLine()) != null) {

                String[] res1 = l1.split("=");
                String[] res2 = l2.split("=");


                String[] resb1 = res1[1].split("%");
                String[] resb2 = res2[1].split("%");

                d.add(Math.abs(Double.parseDouble(resb1[0].replace(',', '.')) - Double.parseDouble(resb2[0].replace(',', '.'))));

                //System.out.println(Arrays.toString(res2));

            }

            Double re = 0.0;
            Double i = 0.0;
            for (Double truc : d) {
                re += truc;
                i++;
            }
            return (re / i);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return 0;
    }

    private static void genereTests(File f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {


            ArrayList<Integer> nlist = new ArrayList<>();


            nlist.add(10);
            nlist.add(25);
            nlist.add(50);
            nlist.add(75);
            nlist.add(100);
          /*  nlist.add(150);
            nlist.add(200);
            */


            ArrayList<Double> plist = new ArrayList<>();
            plist.add(0.0);
            plist.add(0.1);
            plist.add(0.2);
            plist.add(0.3);
            plist.add(0.4);
            plist.add(0.5);
            plist.add(0.6);
            plist.add(0.7);
            plist.add(0.8);
            plist.add(0.9);
            plist.add(1.0);


            ArrayList<Double> rlist = new ArrayList<>();
            rlist.add(0.0);
            rlist.add(0.1);
            rlist.add(0.2);
            rlist.add(0.3);
            rlist.add(0.4);
            rlist.add(0.5);
            rlist.add(0.6);
            rlist.add(0.7);
            rlist.add(0.8);
            rlist.add(0.9);
            rlist.add(1.0);


            bw.write("Debut : " + new Date(System.currentTimeMillis()).toString());

            bw.newLine();
            bw.write("nombre d'essais : 1000");
            bw.newLine();
            bw.write("(n,p,r)= probants %");
            bw.newLine();


            for (int n : nlist) {
                for (Double r : rlist) {
                    for (Double p : plist) {
                        String e = "(" + n + "," + p + "," + r + ")=" + Main.repeatRandom(n, p, r, 1000);
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


    public static double getT(int n, double p) {
        //System.out.println("n:" + n + " p:" + p);

        List<Double> ld = new ArrayList<>();
        HashMap<Double, Double> nearbyMap = new HashMap<>();
        for (double i = 0.0; i < 1.0; i += 0.05) {
            ld.add(i);

        }

        /**
         * On stocke la difference entre le resultat et 1/2
         * Plus c'est petit plus on s'approche de 1/2
         */
        //System.out.print("nearby ");
        ld.forEach(r -> {
            //System.out.print(r + " ");
            nearbyMap.put(r, Math.abs(Main.repeatRandom(n, p, r, 1000) - 0.5));
        });
        //System.out.println();
        //System.out.println("map 1: ");
        //nearbyMap.forEach((est, nord) -> System.out.println(est + " | " + nord));
        /**
         * on cherche le resultat le plus proche de 1/2 donc la diff la plus petite
         *
         */
        double minv = Double.MAX_VALUE;
        double k = -1.0;
        for (double r : nearbyMap.keySet()) {
            double temp = nearbyMap.get(r);
            if (temp < minv) {
                minv = temp;
                k = r;
            }
        }
        /**
         * il est maintenant dans k ( c'est la clé)
         */
        //System.out.println("k:" + k + " m:" + minv);
        nearbyMap.clear();
        ld.clear();

        double pas = 0.005;


        /**
         * on regarde si en diminuant ou en augmentant d'un yota on s'approche plus
         */

        double diffplus = Math.abs(Main.repeatRandom(n, p, k + pas, 1000) - 0.5);
        double diffmoins = Math.abs(Main.repeatRandom(n, p, k - pas, 1000) - 0.5);


/**
 * on regarde si on doit -- ou ++
 */
        if (diffmoins < diffplus) {
            //System.out.println("cas --");
            Double kfixe = k;
            for (double i = kfixe; i < k - 0.05; i -= pas) {
                ld.add(i);

            }
            //System.out.print("nearby ");
/**
 * on remet des resultats dans la map
 */
            ld.forEach(r -> {
                //System.out.print(r + " ");
                nearbyMap.put(r, Math.abs(Main.repeatRandom(n, p, r, 1000) - 0.5));
            });


        } else {
            //cas++
            //System.out.println("cas ++");
            Double kfixe = k;
            for (double i = kfixe; i < k + 0.05; i += pas) {
                ld.add(i);

            }
            //System.out.print("nearby ");

            ld.forEach(r -> {
                //System.out.print(r + " ");
                nearbyMap.put(r, Math.abs(Main.repeatRandom(n, p, r, 1000) - 0.5));
            });
        }
        //System.out.println("map 2: ");
        //nearbyMap.forEach((est, nord) -> System.out.println(est + " | " + nord));

/**
 * on cherche encore le r pout lequel on est le plus proche de 1/2
 */
        double key = Double.MAX_VALUE;
        minv = Double.MAX_VALUE;

        for (double r : nearbyMap.keySet()) {
            double temp = nearbyMap.get(r);
            if (temp < minv) {
                minv = temp;
                key = r;
            }
        }
        //System.out.println("k:" + key + " m:" + minv);
        return key;
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

    }
}
