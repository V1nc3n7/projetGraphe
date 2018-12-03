import graphe.Graphe;

import java.text.DecimalFormat;

/**
 * @autor Vincent
 * @date 03/11/2018
 */

class Main {
    private static final int NSUFFISEMENTGRAND = 1000;
    private static final double TARGET = 0.5;

    /**
     * Retourne la probabilité qu'un graphe admette une séquence 2-destructrice
     *
     * @param nSommets      nombre de sommmets du graphe
     * @param probablite    proba que les arretes apartiennent au graphe
     * @param rougirSommets proba qu'un sommet soit coloré en rouge
     */
    private static Double testA(int nSommets, double probablite, double rougirSommets) {
        float experiences, probants;
        DecimalFormat format = new DecimalFormat("#.##");
        experiences = 0;
        probants = 0;
        while (experiences != NSUFFISEMENTGRAND) {
            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            experiences++;
            if (graphe.isSquencePossible()) {
                probants++;
            }
        }
        String res = format.format((probants / experiences));
        res = res.replace(",", ".");
        return Double.valueOf(res) * 100;
    }

    /**
     * Retourne la probabilité qu'un graphe admette une séquence 2-destructrice pour nb expériences
     *
     * @param nSommets      nombre de sommmets du graphe
     * @param probablite    proba que les arretes apartiennent au graphe
     * @param rougirSommets proba qu'un sommet soit coloré en rouge
     * @param nb            nombre d'expériences réalisées
     */
    private static double repeatRandom(int nSommets, double probablite, double rougirSommets, int nb) {
        double expriences, probants;
        expriences = probants = 0;
        while (expriences != nb) {
            expriences++;
            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            if (graphe.isSquencePossible()) {
                probants++;
            }
        }
        return (probants / expriences);
    }

    /**
     * Retourne la probabilité qu'un sommet soit rouge avec une probabilité d'avoir une
     * séquence 2-destructrice la plus proche de 1/2
     *
     * @param nSommets   nombre de sommmets du graphe
     * @param probablite proba que les arretes apartiennent au graphe
     */
    private static Double testB(int nSommets, double probablite) {
        System.out.print("Itérations:1 ");
        DecimalFormat format = new DecimalFormat("#.####");
        int iteration = 1;
        double borne_bas = 0;
        double borne_haut = 1;
        double milieu = borne_haut / 2;
        double res_borne_bas = repeatRandom(nSommets, probablite, borne_bas, NSUFFISEMENTGRAND);
        double res_milieu = repeatRandom(nSommets, probablite, milieu, NSUFFISEMENTGRAND);

        while (!format.format(borne_bas).equals(format.format(borne_haut))) {
            if ((TARGET >= res_borne_bas && TARGET < res_milieu) || (TARGET <= res_borne_bas && TARGET > res_milieu)) {
                borne_haut = milieu;
                milieu = borne_bas + (milieu - borne_bas) / 2;
            } else {
                borne_bas = milieu;
                milieu = milieu + (borne_haut - milieu) / 2;
            }
            iteration++;
            res_borne_bas = repeatRandom(nSommets, probablite, borne_bas, NSUFFISEMENTGRAND);
            res_milieu = repeatRandom(nSommets, probablite, milieu, NSUFFISEMENTGRAND);
            System.out.print(iteration + " ");
        }
        String res = format.format(milieu);
        res = res.replace(",", ".");
        return Double.valueOf(res);
    }

    public static void main(String[] args) {
        //Valeurs par défault
        //TestA
        double rougirSommets = 0.5;
        //TestA et TestB
        int nSommets = 100;
        double probablite = 0.7;

        /*On lance testA et testB avec les valeurs de base*/
        if (args.length == 0 || (!args[0].equals("testb") && !args[0].equals("testa"))) {
            System.out.println("testB(nbsommets:" + nSommets + ",probabilitéArretes:" + probablite + ")=" + testB(nSommets, probablite) * 100 + "%");
        } else {
            if (args[0].equals("testa") && args.length == 4) {
                nSommets = Integer.valueOf(args[1]);
                probablite = Double.valueOf(args[2]);
                rougirSommets = Double.valueOf(args[3]);
                System.out.println("testA(nbsommets:" + nSommets + ",probabilitéArretes:" + probablite + ",probaRougirSommets:" + rougirSommets + ")=" + testA(nSommets, probablite, rougirSommets) + "%");
            } else {
                System.out.println("Paramètres invalides");
            }
            if (args[0].equals("testb") && args.length == 3) {
                nSommets = Integer.valueOf(args[1]);
                probablite = Double.valueOf(args[2]);
                System.out.println("testB(nbsommets:" + nSommets + ",probabilitéArretes:" + probablite + ")=" + testB(nSommets, probablite) * 100 + "%");
            } else {
                System.out.println("Paramètres invalides");
            }
        }
    }
}