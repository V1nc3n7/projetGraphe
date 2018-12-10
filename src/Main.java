import graphe.Graphe;

import java.text.DecimalFormat;

/**
 * @autor Vincent
 * @date 03/11/2018
 */

class Main {
    private static final int NSUFFISEMENTGRAND = 1000;//Nombre d'expérience suffisament grand
    private static final double TARGET = 0.5;//Probabilité visée que le graphe n'admette pas de séquence n-destructrice
    //Valeurs par defaut
    private static final int NSOMMETS=50; //Nombre de sommets dans le graphe
    private static final double ROUGIRSOMMETS=0.5;//Probabilité qu'un sommet soit rouge
    private static final double PROBABILITE=0.1; //Probabilité que chaque arrete possible appartienne au graphe

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
            Graphe graphe = new Graphe(nSommets, probablite, rougirSommets);
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
     *  @param nSommets      nombre de sommmets du graphe
     * @param probablite    proba que les arretes apartiennent au graphe
     * @param rougirSommets proba qu'un sommet soit coloré en rouge
     */
    private static double repeatRandom(int nSommets, double probablite, double rougirSommets) {
        double expriences, probants;
        expriences = probants = 0;
        while (expriences != Main.NSUFFISEMENTGRAND) {
            expriences++;
            Graphe graphe = new Graphe(nSommets, probablite, rougirSommets);
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
        System.out.print("Itérations : 1 ");
        DecimalFormat format = new DecimalFormat("#.####");
        int iteration = 1;
        double borne_bas = 0;
        double borne_haut = 1;
        double milieu = borne_haut / 2;
        double res_borne_bas = repeatRandom(nSommets, probablite, borne_bas);
        double res_milieu = repeatRandom(nSommets, probablite, milieu);

        while (!format.format(borne_bas).equals(format.format(borne_haut))) {
            if ((TARGET >= res_borne_bas && TARGET < res_milieu) || (TARGET <= res_borne_bas && TARGET > res_milieu)) {
                borne_haut = milieu;
                milieu = borne_bas + (milieu - borne_bas) / 2;
            } else {
                borne_bas = milieu;
                milieu = milieu + (borne_haut - milieu) / 2;
            }
            iteration++;
            res_borne_bas = repeatRandom(nSommets, probablite, borne_bas);
            res_milieu = repeatRandom(nSommets, probablite, milieu);
            System.out.print(iteration + " ");
        }
        System.out.println(" ");
        String res = format.format(milieu);
        res = res.replace(",", ".");
        return Double.valueOf(res);
    }

    public static void main(String... args) {
        double rougirSommets;
        int nSommets;
        double probablite;

        //Test A et B avec les paramètres par défaut
        if (args.length == 0 || (!args[0].equals("testb") && !args[0].equals("testa"))) {
            System.out.println("testA(nbsommets:" + NSOMMETS + ",probabilitéArretes:" + PROBABILITE + ",probaRougirSommets:" + ROUGIRSOMMETS + ")=" + testA(NSOMMETS, PROBABILITE, ROUGIRSOMMETS) + "%");
            System.out.println("testB(nbsommets:" + NSOMMETS + ",probabilitéArretes:" + PROBABILITE + ")=" + testB(NSOMMETS, PROBABILITE) * 100 + "%");
        }else{
            switch(args[0]){
                //Test A avec paramètres de l'utilisateur
                case "testa":
                    if (args.length == 4) {
                        nSommets = Integer.valueOf(args[1]);
                        probablite = Double.valueOf(args[2]);
                        rougirSommets = Double.valueOf(args[3]);
                        System.out.println("testA(nbsommets:" + nSommets + ",probabilitéArretes:" + probablite + ",probaRougirSommets:" + rougirSommets + ")=" + testA(nSommets, probablite, rougirSommets) + "%");
                    }  else {
                        System.out.println("Paramètres invalides");
                    }
                    break;
                //Test B avec paramètres de l'utilisateur
                case "testb":
                    if (args.length == 3) {
                        nSommets = Integer.valueOf(args[1]);
                        probablite = Double.valueOf(args[2]);
                        System.out.println("testB(nbsommets:" + nSommets + ",probabilitéArretes:" + probablite + ")=" + testB(nSommets, probablite) * 100 + "%");
                    } else {
                        System.out.println("Paramètres invalides");
                    }
                    break;
            }

        }
    }
}