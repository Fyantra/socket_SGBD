package main;

import java.util.Vector;

import fonction.*;

import objets.*;

import inputOutput.InputOutput;

public class Main {
    public static void main(String[] args) throws Exception {


        Fonction f = new Fonction();
        Donnee d = new Donnee();

        String requeteJoueur = "select nomJoueur,equipe from joueur";
        String requetePays = "select idPays,nomPays,continent from pays";
        String requetePersonne = "select nom,age,job from personne ";

        /////Create table   
         String requeteCreate = "create table dodo ( dodo1 type1 , dodo2 type2 , dodo3 type3 )";

        ////Insert 
         String requeteInsert = "insert into dodo values ( didi1, dada2, dudu3 )";

        try {
            //Table relation = f.traiterRequete(requeteCreate);
            //Table relationJoueur = f.fetchRequest(requeteJoueur);
            Table relationPersonne = f.fetchRequest(requetePersonne);
            //f.fetchRequest(requeteCreate);
            //f.fetchRequest(requeteInsert);
            f.displayResult(relationPersonne);
            //f.displayResult(relationPays);
            
           // d.setDatabaseInFile();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    
    }

}