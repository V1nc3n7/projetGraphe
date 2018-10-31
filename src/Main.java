import graphe.Graphe;
import graphe.Sequence2destructrice;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Main {
    public static void main(String ... args){

        Graphe grapheA = new Graphe("res/grapheA.txt");
        Graphe grapheB = new Graphe("res/grapheB.txt");
        Sequence2destructrice s = new Sequence2destructrice("v1 v6 v3 v8 v2 v5 v4 v7");
        Sequence2destructrice s1 = new Sequence2destructrice("v2 v6 v1 v5 v4 v3 v7 v8");

        System.err.println(grapheA.isSeq2destr(s));
        System.err.println(grapheB.isSeq2destr(s1));





    }
}
