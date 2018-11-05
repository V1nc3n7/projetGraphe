package resets;

/**
 * @autor Vincent
 * @date 05/11/2018
 */

public class Resultat {
    private Double parametre;
    private Double valeur;

    public Resultat(Double parametre, Double valeur) {
        this.parametre = parametre;
        this.valeur = valeur;
    }

    public double getParametre() {
        return parametre;
    }

    public double getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return "[" + parametre + "] " + valeur;
    }
}
