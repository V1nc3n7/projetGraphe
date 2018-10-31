import graphe.Graphe;
import graphe.Sequence2destructrice;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Main {
    public static void main(String ... args){

        Graphe grapheA = new Graphe("res/grapheA.txt");
        grapheA.print();
        Sequence2destructrice s = new Sequence2destructrice("v1 v6 v3 v8 v2 v5 v4 v7");

        System.out.println(grapheA.isSeq2destr(s));

        /*
        Graphe grapheB = new Graphe("res/grapheB.txt");
        grapheB.print();
       */

    }
}
