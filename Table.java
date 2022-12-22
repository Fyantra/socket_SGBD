package objets;


public class Table {    //classe pouvant etre serialisee
    String nom;
    Object[] entete;
    Object[][] data;

    public Table(){}

    public Table(Object[]e, Object[][] d) {
        setEntete(e);
        setData(d);
    }

    public Table(String n, Object[]e, Object[][] d) {
        setNom(n);
        setEntete(e);
        setData(d);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Object[] getEntete() {
        return entete;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public void setEntete(Object[] entete) {
        this.entete = entete;
    }
}