package fonction;

import java.util.*;

import syntaxe.Syntaxe;
import inputOutput.InputOutput;
import objets.*;
import fonction.Operation;

import java.lang.reflect.*;

public class Fonction {

    /////Les fonctions Statiques
    public static String [] getDonneeInsertion (String request) {
        String s1 = request.substring(request.lastIndexOf("(") + 1, request.lastIndexOf(")"));
        
        //split les donnees par ","
        String[] s2 = s1.split(",");
        
        String [] sRep = Fonction.getStringWithoutSpace(s2);   
        
        return sRep;
    }

    public static String [][] getTypeColonne (String request) {
        String s1 = request.substring(request.lastIndexOf("(") + 1, request.lastIndexOf(")"));
        
        String[] s2 = s1.split(",");
        
        String [][] type = new String[s2.length][2]; // [i][0] nomCol ; [i][1] type
        
        int i = 0;
        
        for (String typeSep : s2) {
            String [] typeOutSp = typeSep.split(" ");
            
            String [] typeTrim = getStringWithoutSpace(typeOutSp);  
            
            type[i] = typeTrim;
            i++;           
        }
        
        return type;
    }

    public Object [][] fetchTo2D (Table t) throws Exception {
        
        Object[][] lo2 = t.getData();
        
        Object[][]rep = new Object[lo2.length][lo2[0].length];
        
        Vector vToTri = new Vector();
        
        
        for (int i = 0; i < lo2.length; i++) {
            vToTri.add(lo2[i][0]); 
        }

        InputOutput e = new InputOutput();
        
        Object[] type = e.getTypesTableInFile(t.getNom());
        
        String typeStr = type[0].toString();
        
        if(typeStr.equalsIgnoreCase("string") == false && typeStr.equalsIgnoreCase("date") == false && typeStr.equalsIgnoreCase("boolean") == false ){
        
            vToTri.sort(null); //tri numerique
        
            Object[] indTri = vToTri.toArray();
        
            for (Object[] ligne : lo2) {
                for (int i = 0; i < indTri.length; i++) {
                    if(Integer.parseInt(indTri[i].toString()) == Integer.parseInt(ligne[0].toString())) {
                        rep[i] = ligne;
                    }
                }
            }
        } 
        else {
            
            Collections.sort(vToTri); // tri alpha
           
            Object[] indTri = vToTri.toArray();
        
            for (Object[] ligne : lo2) {
                for (int i = 0; i < indTri.length; i++) {
                    if(indTri[i].toString().compareTo(ligne[0].toString()) == 0) {
                        rep[i] = ligne;
                        
                    }
                }
            }
        }

        return rep;
    }

    public Object [][] fetchTo2D (Object[][] lo2) throws Exception {
        Table t = new Table();
        Object[][]rep = new Object[lo2.length][lo2[0].length];
        Vector vToTri = new Vector();
        for (int i = 0; i < lo2.length; i++) {
            vToTri.add(lo2[i][0]); 
        }

        InputOutput in = new InputOutput();
        Object[] type = in.getTypesTableInFile(t.getNom());
        String typeStr = type[0].toString();
        if(typeStr.equalsIgnoreCase("string") == true) {
            vToTri.sort(null); //tri
        } 
        
        Object[] indTri = vToTri.toArray();
        
        for (Object[] ligne : lo2) {
            for (int i = 0; i < indTri.length; i++) {
                if(Integer.parseInt(indTri[i].toString()) == Integer.parseInt(ligne[0].toString())) {
                    rep[i] = ligne;
                }
            }
        }

        return rep;
    }

    public Object[][] updateMitovy(Table t) throws Exception {
        Object[][] lo = t.getData();
        
        InputOutput in = new InputOutput();
        
        Object[] type = in.getTypesTableInFile(t.getNom());
        
        Object[][] obj = lo;
       
            obj = fetchTo2D(t);
         
        Vector v = new Vector();
       
        Object[] ligneInitial = obj[0];
       
        v.add(ligneInitial);

        for (int i = 0; i < obj.length; i++) {
            
            if(isDoublureString(ligneInitial, obj[i]) == false) {
                ligneInitial = obj[i];
                v.add(ligneInitial);
            }
        }

        Object[][]rep =getByvector(v);
        return rep;
    }

    public Object[][] updateMitovy(Object[][] lo) throws Exception {
        InputOutput in = new InputOutput();
        
        Object[] type = in.getTypesTableInFile("joueurr");
        
        Object[][] obj = lo;
        if(type[0].toString().equalsIgnoreCase("string") == false) {
            obj = fetchTo2D(lo);
        }
        
        Vector v = new Vector();
        
        Object[] ligneInitial = obj[0];
        
        v.add(ligneInitial);

        for (int i = 0; i < obj.length; i++) {
            //System.out.println(isDoublure(ligneInitial, obj[i]));
            if(isDoublureString(ligneInitial, obj[i]) == false) {
                ligneInitial = obj[i];
                v.add(ligneInitial);
            }
        }

        Object[][]rep =getByvector(v);
        return rep;
    }

    public boolean isDoublure(Object[] lo1, Object[] lo2) {
        if(lo1.equals(lo2)) {
            return true;
        }
        return false;
    }

    public Object[][] merge2Obj(Object[][] l21, Object[][] l22) {
        int nbLignes = l21.length + l22.length;
        
        int nbCol = l21[0].length;
        
        Object[][] rep = new Object[nbLignes][nbCol];
        
        int indLigne = 0;
        
        int indCol = 0;

        for (int i = 0; i < l21.length; i++) {
            for (int j = 0; j < nbCol; j++) {
                rep[indLigne][indCol] = l21[i][j];
                indCol++;
            }
            indLigne++;
            indCol = 0;
        }

        indCol = 0;
        for (int k = 0; k < l22.length; k++) {
            for (int l = 0; l < nbCol; l++) {
                rep[indLigne][indCol] = l22[k][l];
                indCol++;
            }
            indLigne++;
            indCol = 0;
        }

        return rep;
    }


    ////////////////////////////////////////////////////////////////
    
    public int getIndColonne(String colonne, Table table) throws Exception {    //maka anle indicenle colonne mitovy aminazy
        Object[] colonnes = table.getcolonne();
        
        for (int i = 0; i < colonnes.length; i++) {
            
            if(colonne.equalsIgnoreCase(colonnes[i].toString()) == true) {
                return i;
            }
        }

        throw new Exception("Aucune colonne ne correspond a "+colonne);
    }

    public boolean getIntTab(int n, int[] t) throws Exception {
        if(t.length == 0) throw new Exception("null ");

        for (int i : t) {
            
            if(n == i) return true;
        }

        return false;
    }

    public static String[] getStringWithoutSpace(String [] s) {  //maka anle string avec trimmm
        Vector v = new Vector<String>();
        
        for (int i = 0; i < s.length; i++) {
            
            if(s[i] != "") {
                
                v.add(s[i].trim());   //supprime les espaces blancs aux 2 extremites de cette chaine
                
            }
        }
        System.out.println();
        
        String [] rep = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            rep[i] = (String) v.get(i);
        }
        return rep;
    }

    public Syntaxe[] getSyntaxeOperation () {
        Syntaxe union = new Syntaxe("union"); Syntaxe inter = new Syntaxe("intersection"); Syntaxe from1 = new Syntaxe("from");
        Syntaxe from = new Syntaxe("from"); Syntaxe and = new Syntaxe("and"); Syntaxe join = new Syntaxe("join");
        Syntaxe produit = new Syntaxe("produit"); Syntaxe difference = new Syntaxe("difference"); //difference
        Syntaxe in = new Syntaxe("in"); Syntaxe division = new Syntaxe("division");
        union.setUnion(from); inter.setUnion(from); join.setUnion(from); produit.setUnion(from);//from1.setUnion(from);
        
        //<------------Syntaxe------------------->
        Syntaxe[] syntaxes = {union, inter, from1, and, join, produit, difference, in, division};
        return syntaxes;
    }

    public Syntaxe[] getLanguageSQL () {
        Syntaxe select = new Syntaxe("select"); Syntaxe etoile = new Syntaxe("*");
        Syntaxe in = new Syntaxe("in"); Syntaxe and = new Syntaxe("and");
        Syntaxe from = new Syntaxe("from"); Syntaxe create = new Syntaxe("create");
        Syntaxe table = new Syntaxe("table"); Syntaxe values = new Syntaxe("values"); 
        Syntaxe insert = new Syntaxe("insert"); Syntaxe into = new Syntaxe("into");

        //<-----------Syntaxe---------------->
        Syntaxe[] sql = { select, etoile, in, and, from, create, table, values, insert, into}; 
        return sql;
    }
    
    //************************************************************************************* */

    public Table fetchRequest(String request) throws Exception {
        System.out.println("Votre requete : "+request);

         InputOutput in = new InputOutput();

        String[] requestD = request.split(" ");
        
        String [] votreRequete = getStringWithoutSpace(requestD);
        
        Syntaxe[] languageSql = getLanguageSQL();
        
        Table relation = new Table();

        //////////////////CREATE/////////////////////////
        if(votreRequete[0].equalsIgnoreCase(languageSql[0].getSyntaxe()) == false) {
            //Si false throw new Exception requete invalide--------------

            if(votreRequete[0].equalsIgnoreCase(languageSql[5].getSyntaxe()) == true) { // create table
                
                if(votreRequete[1].equalsIgnoreCase(languageSql[6].getSyntaxe()) == true) { //le table
                    
                    String nomTable = votreRequete[2]; //nomTable
                    
                    int tailleReq = votreRequete.length;
                    
                    if(votreRequete[tailleReq - 1].equalsIgnoreCase(")") == true) { 
                        
                        String [][] colType = getTypeColonne(request);
                        
                        in.insertDescribe(nomTable, colType);
                        
                        Object[] descCreated = in.getcolonneTable(nomTable);
                        
                        Object[][]message = new Object[1][descCreated.length];
                        
                        for (int i = 0; i < descCreated.length; i++) {
                            message[0][i] = "--";
                            
                        }
                        System.out.println("Table creee");
                        
                        relation = new Table(descCreated, message);
                        
                        relation.setNom(nomTable);
                    }
                    else {
                        throw new Exception("erreur de syntaxe");
                    }
                }
                else {
                    throw new Exception("erreur de syntaxe");
                }
            }

            else if(votreRequete[0].equalsIgnoreCase(languageSql[8].getSyntaxe()) == true) { //insert
                
                if(votreRequete[1].equalsIgnoreCase(languageSql[9].getSyntaxe()) == true) { //into
                    
                    String nomTable = votreRequete[2];
                    
                    String [] values = getDonneeInsertion(request);
                    
                    in.insertData(nomTable, values);    
                    
                    System.out.println("insertion effectuee avec succes");
                    
                    Object[][] data = in.lire(nomTable);
                    
                    Object[] colonne = in.getcolonneTable(nomTable);
                    
                    relation = new Table(colonne, data);
                    
                    relation.setNom(nomTable);
                }
                else {
                    throw new Exception("requete invalide");
                }
            }
        
        
        }
        else if(votreRequete[0].equalsIgnoreCase(languageSql[0].getSyntaxe()) == true){     //true ny select
        
            ////////////////UNION///////////////////////
            if(votreRequete[1].equalsIgnoreCase(languageSql[1].getSyntaxe()) == true) {    //ra etoile
                
                Syntaxe [] syntaxe = getSyntaxeOperation();
                
                if(votreRequete[2].equalsIgnoreCase(syntaxe[0].getSyntaxe()) == true) {    //Union
                    
                    if(votreRequete[3].equalsIgnoreCase(syntaxe[2].getSyntaxe()) == true) { //frommmm
                        
                        String table = votreRequete[4];
                        
                        if(in.isInDatabase(table) == true) {
                            
                            if(votreRequete[5].equalsIgnoreCase(syntaxe[3].getSyntaxe()) == true) { //anddd
                                
                                String table2 = votreRequete[6];
                                
                                if(in.isInDatabase(table2) == true) {
                                    
                                    Operation op = new Operation();
                                    
                                    Object[] colonne1 = in.getcolonneTable(table);
                                    
                                    Object[][] data1 = in.lire(table); 
                                    
                                    Table t1 = new Table(colonne1, data1);
                                    
                                    t1.setNom(table);
                                    
                                    Object[] colonne2 = in.getcolonneTable(table2);
                                    
                                    Object[][] data2 = in.lire(table2); 
                                    
                                    Table t2 = new Table(colonne2, data2);
                                    
                                    t2.setNom(table2);

                                    System.out.println("UNION");
                                    
                                    return op.union(t1, t2);
                                }
                                else throw new Exception("il y a une erreur");
                            }

                            System.out.println("une erreur s`est survenue");
                        }
                        else {
                            System.out.println("aucun table de cette nom");
                        }
                    }
                    else {
                        throw new Exception("request invalide a partir de "+votreRequete[3]);
                    }
                }
                
                /////////////////intersection///////////////////////// 
                else if(votreRequete[2].equalsIgnoreCase(syntaxe[1].getSyntaxe()) == true) {
                    
                    if(votreRequete[3].equalsIgnoreCase(syntaxe[1].getUnion().getSyntaxe()) == true) { //frommm
                        
                        String table = votreRequete[4];
                        
                        if(in.isInDatabase(table) == true) {
                            
                            if(votreRequete[5].equalsIgnoreCase(syntaxe[3].getSyntaxe()) == true) { //anddd
                                
                                String table2 = votreRequete[6];
                                
                                if(in.isInDatabase(table2) == true) {
                                    
                                    Operation op = new Operation();
                                    
                                    Object[] colonne1 = in.getcolonneTable(table);
                                    
                                    Object[][] data1 = in.lire(table); 
                                    
                                    Table t1 = new Table(colonne1, data1);
                                    
                                    t1.setNom(table);
                                    
                                    Object[] colonne2 = in.getcolonneTable(table2);
                                    
                                    Object[][] data2 = in.lire(table2);
                                    
                                    Table t2 = new Table(colonne2, data2);
                                    
                                    t2.setNom(table2);

                                    System.out.println("INTERSECTION");
                                    
                                    Table inter = op.intersection(t1, t2);
                                    
                                    return inter;
                                }
                                else throw new Exception("aucun database ne correspond a "+ table2);
                            }
                            else throw new Exception("requete invalide");
                        }
                        else {
                            throw new Exception("aucun database ne correspond a "+ table);
                        }
                    }
                    else {
                        System.out.println("requete invalide");
                    }
                }
                //////////////////////////SELECTION/////////////////////////////////////
                else if(votreRequete[2].equalsIgnoreCase(syntaxe[2].getSyntaxe()) == true) {
                    
                    String table = votreRequete[3];
                    
                    if(in.isInDatabase(table) == true) {
                        
                        String where = "where";
                        
                        if(votreRequete[4].equalsIgnoreCase(where) == true) {
                            
                            String colonne = votreRequete[5];
                            
                            if(in.checkColonne(colonne, table) == true) {
                                
                                String operateur = votreRequete[6];
                                
                                Object[] colonnes = in.getcolonneTable(table);
                                
                                Object[][] data = in.lire(table);
                                
                                Table selectAll = new Table(table,colonnes, data);
                                
                                selectAll.setNom(table);
                                
                                String condition = votreRequete[7];
                                
                                Operation op = new Operation();
                                
                                
                                return op.selection(selectAll, colonne, operateur, condition);
                            }
                        }
                        System.out.println("requete invalide");
                    }
                }
                
                /////////////////////////////////////////Produit cartesien///////////////////////////
                else if(votreRequete[2].equalsIgnoreCase(syntaxe[5].getSyntaxe()) == true) {
                    
                    if(votreRequete[3].equalsIgnoreCase(syntaxe[0].getUnion().getSyntaxe()) == true) { //frommmm
                        
                        String table = votreRequete[4];
                        
                        if(in.isInDatabase(table) == true) {
                            
                            if(votreRequete[5].equalsIgnoreCase(syntaxe[3].getSyntaxe()) == true) { //sy
                                
                                String table2 = votreRequete[6];
                                
                                if(in.isInDatabase(table2) == true) {
                                    
                                    Operation op = new Operation();
                                    
                                    Object[] colonne1 = in.getcolonneTable(table);
                                    
                                    Object[][] data1 = in.lire(table); 
                                    
                                    Table t1 = new Table(colonne1, data1);
                                    
                                    t1.setNom(table);
                                    
                                    Object[] colonne2 = in.getcolonneTable(table2);
                                    
                                    Object[][] data2 = in.lire(table2); 
                                    
                                    Table t2 = new Table(colonne2, data2);
                                    
                                    t2.setNom(table2);
                                    
                                    return op.produitCartesien(t1, t2);
                                }
                                else throw new Exception("aucun database ne correspond a "+ table2);
                            }

                            System.out.println("requete invalide");
                        }
                        else {
                            throw new Exception("aucun database ne correspond a "+ table);
                        }
                    }
                    else {
                        System.out.println("requete invalide");
                    }
                }

                //////////////////////////////difference/////////////////////////////// 
                else if(votreRequete[2].equalsIgnoreCase(syntaxe[6].getSyntaxe()) == true) {
                    
                    if(votreRequete[3].equalsIgnoreCase(syntaxe[2].getSyntaxe()) == true) { //from
                        
                        String table = votreRequete[4];
                        
                        if(in.isInDatabase(table) == true) {
                            
                            if(votreRequete[5].equalsIgnoreCase(syntaxe[7].getSyntaxe()) == true) { // innnn
                                
                                String table2 = votreRequete[6];
                                
                                if(in.isInDatabase(table2) == true) {
                                    
                                    Operation op = new Operation();
                                    
                                    Object[] colonne1 = in.getcolonneTable(table);
                                    
                                    Object[][] data1 = in.lire(table); 
                                    
                                    Table t1 = new Table(colonne1, data1);
                                    
                                    t1.setNom(table);
                                    
                                    Object[] colonne2 = in.getcolonneTable(table2);
                                    
                                    Object[][] data2 = in.lire(table2); 
                                    
                                    Table t2 = new Table(colonne2, data2);
                                    
                                    t2.setNom(table2);
                                    
                                    return op.difference(t1,t2);
                                }
                                else throw new Exception("aucun database ne correspond a "+ table2);
                            }

                            else
                                throw new Exception("requete invalide a partir de " + votreRequete[5]);
                        }
                        else {
                            throw new Exception("aucun database ne correspond a "+ table);
                        }
                    }
                    else {
                        System.out.println("requete invalide");
                    }
                }

                /////////////////////////////////division///////////////////////////////// 
                
                else {
                    throw new Exception("request invalide a partir de "+votreRequete[2]);
                }
            }

            /////PROJECTION/////////////////////////////////
            else {
                System.out.println("PROJECTION");
                
                System.out.println("");
                
                String [] colonnes = votreRequete[1].split(",");
                

                //frommm
                if(votreRequete[2].equalsIgnoreCase(languageSql[4].getSyntaxe()) == false) 
                    
                    throw new Exception("request invalide");

                //frrrrrrrrroooooommmmmmmmmmmmmmmmmmm
                else {
                    String table = votreRequete[3];
                    
                    Operation op = new Operation();
                    
                    Object[] colonne = in.getcolonneTable(table);
                    
                    Object[][] data = in.lire(table);
                    // relation = new Table(colonne, data); 
                    relation = op.projection(colonne, data, colonnes);
                    
                    relation.setNom(table);
                }
            }
        }
        return relation;
    }

    public Object[][] getByvector(Vector v) {
        Object[] data = (Object[]) v.get(0);
        
        Object[][] rep = new Object[v.size()][data.length];
        
        for (int i = 0; i < v.size(); i++) {
            
            Object[] ligne = new Object[data.length];
            
            for(int j = 0; j < data.length; j++) {
                ligne[j] = ((Object[]) v.get(i))[j];
            }
            rep[i] = ligne;
        }
        return rep;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void displayResult(Table table) throws Exception {

        Object[] colonne = table.getcolonne();
        
        Object[][] data = table.getData();

        if (data.length > 0) {

            for (Object attribut : colonne) {
                System.out.print("|    " + attribut.toString() + "    |   "); // colonne
            }
            System.out.println();
            System.out.println("------------------------------------------------------------------");

            for (Object[] lo : data) {
                for (Object o : lo) {
                    if (o != null)
                        System.out.print("   |   " + String.valueOf(o) + "   |   ");
                    else {
                        System.out.println("null");
                    }
                }
                System.out.println("");
                System.out.println("--------------------------------------------------------------------");
            }
        }

        else {
            System.out.println();
            System.out.println("Table vide");
            System.out.println();
        }
    }

    public String [] vectToStringTab(Vector v) throws Exception {
        if(v.size() <= 0) throw new Exception("vector null");

        String [] r = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            r[i] = (String) v.get(i);
        }

        return r;
    }

    public String constrSelection(String [] colonnes)throws Exception {
        int k = 0;
        String c = "";
        for (int i = 0; i < colonnes.length - 1; i++) { //le farany tsy misy virgule
            c = c + colonnes[i] + ",";
            k++;
        }

        c = c + colonnes[k];
        return c;
    }

    public int[] getIndNonCommonCol (Table t1, Table t2) throws Exception {
        int[] indColCom = getIndCommonCol(t1, t2);
        Vector indVect = new Vector();
    
        Object[] colonne1 = t1.getcolonne();
        for (int j = 0; j < colonne1.length; j++) {
            if(getIntTab(j, indColCom) == false) {
                indVect.add(j);
            }
        }
        

       if(indVect.size() == 0) throw new Exception("Pas de colonne non en commun");

       int[] indCol = new int[indVect.size()];
        for (int i = 0; i < indCol.length; i++) {
            indCol[i] = (int) indVect.get(i);
            //System.out.println(indCol[i]);
        }

        return indCol;
    }

    public int[] getIndCommonCol (Table t1, Table t2) throws Exception {
        Vector indVect = new Vector();
        Object[] colonne1 = t1.getcolonne();
        Object [] colonne2 = t2.getcolonne();
        for (int i = 0; i < colonne1.length; i++) {
            for (int j = 0; j < colonne2.length; j++) {
                if(colonne1[i].toString().equals(colonne2[j].toString()) == true) {
                    indVect.add(i);
                }
            }
        }

       if(indVect.size() == 0) throw new Exception("Pas de colonne en commun");

       int[] indCol = new int[indVect.size()];
        for (int i = 0; i < indCol.length; i++) {
            indCol[i] = (int) indVect.get(i);
        }

        return indCol;
    }

    public boolean isInTable(Object[] ligne, Table t) throws Exception {
        Object[][]data = t.getData();
        for (Object[] l : data) {
            if(isDoublureString(ligne, l) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean isDoublureString (Object[] lo1, Object[] lo2) {
        for (int i = 0; i < lo1.length; i++) {
            if(lo1[i].toString().equals(lo2[i].toString()) == false) {
                return false;
            }
        }
        return true;
    }

}