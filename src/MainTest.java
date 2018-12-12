import graphe.Graphe;
import graphe.Sequence2destructrice;

/**
 * Test de Graphes
 */
public class MainTest {


    public static void main(String... args) {


        System.out.println("Création des graphes");
        System.out.println();
        System.out.println("res/grapheA.txt");
        Graphe grapheA = new Graphe("res/grapheA.txt");
        System.out.println("Graphe A");
        grapheA.print();
        System.out.println();

        System.out.println("res/grapheB.txt");
        Graphe grapheB = new Graphe("res/grapheB.txt");
        System.out.println("Graphe B");
        grapheB.print();
        System.out.println();

        System.out.println("Création des sequences");
        Sequence2destructrice s = new Sequence2destructrice("v1 v6 v3 v8 v2 v5 v4 v7");
        Sequence2destructrice s1 = new Sequence2destructrice("v2 v6 v1 v5 v4 v3 v7 v8");
        System.out.println();


        System.out.println("Test des séquences");

        System.out.println("Séquence 1 detruit Ga ? ");
        System.out.println(grapheA.isSeq2destr(s));
        System.out.println("Séquence 2 detruit Gb ? ");
        System.out.println(grapheB.isSeq2destr(s1));
        System.out.println();
        System.out.println("Tests des séquences fausses");
        System.out.println();
        System.out.println("Séquence 1 detruit Ga ? ");
        System.out.println(grapheA.isSeq2destr(s1));
        System.out.println("Séquence 2 detruit Gb ? ");
        System.out.println(grapheB.isSeq2destr(s));
        System.out.println();

        System.out.println("Génération d'une sequence");
        System.out.print("Séquence 1 ");
        Sequence2destructrice seqt = grapheA.generateSequence();
        System.out.println(seqt.toString());
        Sequence2destructrice seqtb = grapheB.generateSequence();
        System.out.print("Séquence 2 ");
        System.out.println(seqtb.toString());
        System.out.println();
        System.out.println("Test des séquences générées");
        System.out.println("Séquence 1 detruit Ga ? ");
        System.out.println(grapheA.isSeq2destr(seqt));
        System.out.println("Séquence 2 detruit Gb ? ");
        System.out.println(grapheB.isSeq2destr(seqtb));
        System.out.println();

        System.out.println("Génération aléatoire n: 30 ,p: 3/5,r: 1/5 ");
        Graphe testG = new Graphe(30, 0.6, 0.2);
        testG.print();

    }
}
