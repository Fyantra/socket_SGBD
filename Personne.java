package objets;

import inputOutput.InputOutput;

public class Personne {
    int idPersonne;
    String nom;
    int age;
    String job;

    public Personne (int id, String nom, int age, String job) {
        setIdPersonne(id);
        setNom(nom);
        setAge(age);
        setJob(job);
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setJob(String job){
        this.job = job;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public String getNom() {
        return nom;
    }
    
    public int getAge() {
        return age;
    }

    public String getJob(){
        return job;
    }
}