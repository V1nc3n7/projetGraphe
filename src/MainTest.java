import graphe.Graphe;
import graphe.Sequence2destructrice;

public class MainTest {


    public static double repeatRandom(int nSommets, double probablite, double rougirSommets, int nb) {
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
        Graphe testG = new Graphe(30, 0.6);
        testG.print();
        System.out.println();

        testG.colorateGraphe(0.2);
        testG.print();

    }
}
