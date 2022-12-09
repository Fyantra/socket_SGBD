package objets;

import java.util.Vector;

public class Joueur {
    String nomJoueur;
    int age;
    double salaire;
    String equipe;

    public Joueur() {
    }

    public Joueur(String nom, int age, double sal, String equipe) {
        setNomJoueur(nom);
        setAge(age);
        setSalaire(sal);
        setEquipe(equipe);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getEquipe(){
        return equipe;
    }

    public void setEquipe(String equipe){
        this.equipe = equipe;
    }
}