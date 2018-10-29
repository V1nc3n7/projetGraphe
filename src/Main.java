import graphe.Graphe;

/**
 * @autor Vincent
 * @date 25/10/2018
 */

public class Main {
    public static void main(String ... args){
        Graphe grapheA = new Graphe("res/grapheA.txt");
        grapheA.print();
        Graphe grapheB = new Graphe("res/grapheB.txt");
        grapheB.print();
    }
}
