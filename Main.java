package main;

import java.util.Vector;

import fonction.*;

import objets.*;

import inputOutput.InputOutput;

public class Main {
    public static void main(String[] args) throws Exception {


        Fonction f = new Fonction();
        Donnee d = new Donnee();

        String requeteJoueur = "select nomJoueur,equipe from joueurr";   //PROJECTION
        String requetePays = "select idPays,nomPays,continent from pays";   //PROJECTION
        String requetePersonne = "select nom,age,job from personne";   //PROJECTION
        
        String conditionPersonne = "select * from personne where age > 40"; //SELECTION
        String conditionPays = "select * from pays where continent = Amerique"; //SELECTION

        String union = "select * union from joueur and personne"; //UNION
        String inter = "select * intersection from joueur and joueur"; //INTERSECTION

        String diff = "select * difference from joueur in personne";    //DIFFERENCE

        /////Create table   
         String requeteCreate = "create table dodo ( dodo1 type1 , dodo2 type2 , dodo3 type3 )";

        ////Insert 
         String requeteInsert = "insert into dodo values ( didi1, dada2, dudu3 )";

        try {
            //Table relation = f.traiterRequete(requeteCreate);
            //Table relationJoueur = f.fetchRequest(requeteJoueur);
            //Table relationPersonne = f.fetchRequest(requetePersonne);
            //Table relationConditionPersonne = f.fetchRequest(conditionPersonne);
            //Table relationConditionPays = f.fetchRequest(conditionPays);
            Table relationUnion = f.fetchRequest(union);
            //Table relationInter = f.fetchRequest(inter);
           // Table relationDiff = f.fetchRequest(diff);
            //f.fetchRequest(requeteCreate);
            //f.fetchRequest(requeteInsert);
            //f.displayResult(relationJoueur);
            //f.displayResult(relationPays);
            //f.displayResult(relationConditionPersonne);
            f.displayResult(relationUnion);

           // d.setDatabaseInFile();    //mampiditra donnee anaty file(ra mbol tsiss zay vo atao)
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    
    }

}