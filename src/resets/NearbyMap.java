package resets;

import java.util.ArrayList;


/**
 * @autor Vincent
 * @date 05/11/2018
 */

public class NearbyMap {
    private ArrayList<Resultat> res;

    private double biais;


    public NearbyMap(double biais) {
        this.res = new ArrayList<>();
        this.biais = biais;

    }

    private void add(Resultat resultat) {

        this.res.add(resultat);
    }

    public void add(double param, double val) {

        this.res.add(new Resultat(param, Math.abs(val - biais)));
    }

    public ArrayList<Resultat> getSet() {
        return this.res;
    }

    public Resultat getResI(int i) {

        return res.get(i);
    }

    public Resultat getBest() {
        Resultat b = res.get(0);


        for (Resultat t : res) {
            if (t.getValeur() < b.getValeur()) {
                b = t;
            }
        }
        return b;
    }

    public double getBestParam() {

        return getBest().getParametre();
    }

    public void print() {
        res.forEach(resultat -> {
            System.out.println(resultat.toString());
        });
    }

    public void clear() {
        this.res.clear();
    }
}
