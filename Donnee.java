package objets;

import java.util.Vector;

import syntaxe.Syntaxe;
import inputOutput.InputOutput;
import objets.*;
import fonction.Operation;

import java.lang.reflect.*;

public class Donnee{

    public Donnee(){

    }

    public void setDatabaseInFile() {
        Joueur j1 = new Joueur("Messi", 35, 30000000, "PSG");
        Joueur j2 = new Joueur("Ronaldo", 37, 50000000, "Manchester United");
        Joueur j3 = new Joueur("Salah", 30, 10500000, "Liverpool");
        Joueur j4 = new Joueur("Neymar", 30, 70000000, "PSG");
        Joueur j5 = new Joueur("Dimaria", 35, 25000000, "Juventus");
        Joueur j6 = new Joueur("Lewandowski", 34, 76000000, "Barcelone");

        Joueur[] listeJoueur = { j1, j2, j3, j4, j5, j6};

        Pays p1 = new Pays(1, "Madagascar", "Afrique", 25000000);
        Pays p2 = new Pays(2, "Pays-Bas", "Europe", 500000);
        Pays p3 = new Pays(3, "Canada", "Amerique", 564000000);
        Pays p4 = new Pays(4, "Chine", "Asie", 500000000);
        Pays p5 = new Pays(5, "Australie", "Oceanie", 300000000);
        Pays p6 = new Pays(6, "Bresil", "Amerique Latine", 630000000);
        
        Pays[] listePays = { p1, p2, p3, p4, p5, p6 };

        Personne pers1 = new Personne(1, "Koto", 26, "Journaliste");
        Personne pers2 = new Personne(2, "Bertrand", 55, "Directeur");
        Personne pers3 = new Personne(3, "Paul", 56, "Ministre");
        Personne pers4 = new Personne(4, "Jean", 41, "Courrier");
        Personne pers5 = new Personne(5, "Joseph", 38, "Pasteur");
        Personne pers6 = new Personne(6, "Dylan", 19, "Etudiant");

        Personne[] listePersonne = { pers1, pers2, pers3, pers4, pers5, pers6 };

        // ecriture fichier
        InputOutput in = new InputOutput();
        try {
            in.insertDescribe(listeJoueur, "joueur");
            in.insertDescribe(listePays, "pays");
            in.insertDescribe(listePersonne, "personne");

            in.insertData(listeJoueur, "joueur");
            in.insertData(listePays, "pays");
            in.insertData(listePersonne, "personne");
            
        } catch (Exception e) {
            System.out.println("ecriture sur fichier echouee");
            System.out.println(e.fillInStackTrace());
        }
    }

    public Vector getDatabase() {
        Joueur j1 = new Joueur("Messi", 35, 30000000, "PSG");
        Joueur j2 = new Joueur("Ronaldo", 37, 50000000, "Manchester United");
        Joueur j3 = new Joueur("Salah", 30, 10500000, "Liverpool");
        Joueur j4 = new Joueur("Neymar", 30, 70000000, "PSG");
        Joueur j5 = new Joueur("Dimaria", 35, 25000000, "Juventus");
        Joueur j6 = new Joueur("Lewandowski", 34, 76000000, "Barcelone");

        Joueur[] listeJoueur = { j1, j2, j3, j4, j5, j6};

        Pays p1 = new Pays(1, "Madagascar", "Afrique", 25000000);
        Pays p2 = new Pays(2, "Pays-Bas", "Europe", 500000);
        Pays p3 = new Pays(3, "Canada", "Amerique", 564000000);
        Pays p4 = new Pays(4, "Chine", "Asie", 500000000);
        Pays p5 = new Pays(5, "Australie", "Oceanie", 300000000);
        Pays p6 = new Pays(6, "Bresil", "Amerique Latine", 630000000);
        
        Pays[] listePays = { p1, p2, p3, p4, p5, p6 };

        Personne pers1 = new Personne(1, "Koto", 26, "Journaliste");
        Personne pers2 = new Personne(2, "Bertrand", 55, "Directeur");
        Personne pers3 = new Personne(3, "Paul", 56, "Ministre");
        Personne pers4 = new Personne(4, "Jean", 41, "Courrier");
        Personne pers5 = new Personne(5, "Joseph", 38, "Pasteur");
        Personne pers6 = new Personne(6, "Dylan", 19, "Etudiant");

        Personne[] listePersonne = { pers1, pers2, pers3, pers4, pers5, pers6 };

        Vector db = new Vector();
        db.add(listeJoueur);
        db.add(listePays);
        db.add(listePersonne);

        return db;
    }

}