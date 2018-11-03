import graphe.Graphe;

import java.text.DecimalFormat;

/**
 * @autor Vincent
 * @date 03/11/2018
 */

public class Main {
    private static final int NSUFFISEMENTGRAND = 1000;

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

    public static Double testB(int nSommets, double probablite) {

        return 0.0;
    }

    public static void main(String... args) {
        System.out.println(testA(50, 1.0, 0.8));

    }
}
