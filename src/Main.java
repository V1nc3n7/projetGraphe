import graphe.Graphe;

import java.text.DecimalFormat;

/**
 * @autor Vincent
 * @date 03/11/2018
 */

public class Main {
    private static final int NSUFFISEMENTGRAND = 1000;

    public static String testA(int nSommets, double probablite, double rougirSommets) {
        float experiences, probants;
        experiences = probants = 0;
        while (experiences != NSUFFISEMENTGRAND) {
            experiences++;
            Graphe graphe = new Graphe(nSommets, probablite);
            graphe.colorateGraphe(rougirSommets);
            if (graphe.isSquencePossible()) probants++;

        }
        return ("Avec n=" + nSommets + " ,p=" + probablite + " et r=" + rougirSommets + " on obtient " + (new DecimalFormat("##.##").format((probants / experiences) * 100) + "%"));
    }


    public static void main(String... args) {
        System.out.println(testA(50, 1.0, 0.8));
    }
}
