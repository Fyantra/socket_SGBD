package objets;

public class Pays {
    int idPays;
    String nomPays;
    String continent;
    double superficie;

    public Pays (int id, String nomP, String con, double superf) {
        setIdPays(id);
        setNomPays(nomP);
        setContinent(con);
        setSuperficie(superf);
    }

    public int getIdPays() {
        return idPays;
    }

    public String getNomPays() {
        return nomPays;
    }

    public String getContinent(){
        return continent;
    }

    public double getSuperficie(){
        return superficie;
    }

    public void setIdPays(int id){
        this.idPays = id;
    }

    public void setNomPays(String n){
        this.nomPays = n;
    }

    public void setContinent(String con){
        this.continent = con;
    }

    public void setSuperficie(double su){
        this.superficie = su;
    }
}