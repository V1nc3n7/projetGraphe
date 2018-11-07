import graphe.Graphe;

import java.text.DecimalFormat;

/**
 * @autor Vincent
 * @date 03/11/2018
 */

public class Main {
    private static final int NSUFFISEMENTGRAND = 1000;
    private static final double TARGET = 0.5;

    public static Double testA(int nSommets, double probablite, double rougirSommets) {
        float experiences, probants;
        experiences = 0;
        probants = 0;
        while (experiences != NSUFFISEMENTGRAND) {
            experiences++;
            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            if (graphe.isSquencePossible()) probants++;

        }
        return Double.valueOf((new DecimalFormat("##.##").format((probants / experiences) * 100)));
    }

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

    public static Double testB(int nSommets, double probablite) {
        DecimalFormat format= new DecimalFormat("#.##");
        int iteration=1;
        double borne_bas=0;
        double borne_haut=1;
        double milieu=borne_haut/2;
        double res_borne_bas = repeatRandom(nSommets,probablite,borne_bas,NSUFFISEMENTGRAND);
        double res_milieu = repeatRandom(nSommets,probablite,milieu,NSUFFISEMENTGRAND);

        System.out.println("Itération: "+ iteration);
        System.out.println("Borne: "+format.format(borne_bas)+"--"+format.format(milieu)+"--"+format.format(borne_haut));

        //parcours dichotomique
        while(!format.format(borne_bas).equals(format.format(milieu)) || !format.format(borne_haut).equals(format.format(milieu))) {
            if((TARGET>=res_borne_bas && TARGET<res_milieu) || (TARGET<=res_borne_bas && TARGET>res_milieu)){
                System.out.println(res_borne_bas+"-->TARGET<--"+res_milieu+"--res_haut");
                borne_haut=milieu;
                milieu=borne_bas+(milieu-borne_bas)/2;
            }else{
                System.out.println(res_borne_bas+"--"+res_milieu+"-->TARGET<--res_haut");
                borne_bas=milieu;
                milieu=milieu+(borne_haut-milieu)/2;
            }
            iteration++;
            res_borne_bas = repeatRandom(nSommets,probablite,borne_bas,NSUFFISEMENTGRAND);
            res_milieu = repeatRandom(nSommets,probablite,milieu,NSUFFISEMENTGRAND);
            System.out.println("Itération: "+ iteration);
            System.out.println("Borne: "+format.format(borne_bas)+"--"+format.format(milieu)+"--"+format.format(borne_haut));
        }
        String res=format.format(milieu);
        res=res.replace(",",".");
        System.out.println("Résultat: "+res+" en "+iteration+" itérations");
        return Double.valueOf(res);
    }

    public static void main(String... args) {
        System.out.println(testA(50, 0.5, 0.5));
        System.out.println(testB(50, 0.1));

    }
}
