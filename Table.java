package objets;


import java.io.Serializable;

public class Table implements Serializable {    //classe pouvant etre serialisee
    String nom;
    Object[] colonne;
    Object[][] data;    
    Table [] isTableSelect;

    public Table(){}

    public Table(Object[]e, Object[][] d) {
        setcolonne(e);
        setData(d);
    }

    public Table(String n, Object[]e, Object[][] d) {
        setNom(n);
        setcolonne(e);
        setData(d);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Object[] getcolonne() {
        return colonne;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public void setcolonne(Object[] colonne) {
        this.colonne = colonne;
    }

    public Table[] getisTableSelect() {
        return isTableSelect;
    }

    public void setisTableSelect(Table[] isTableSelect) {
        this.isTableSelect = isTableSelect;
    }

}