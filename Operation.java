package fonction;

import java.util.Vector;

import syntaxe.Syntaxe;
import inputOutput.InputOutput;
import objets.*;

import java.lang.reflect.*;

public class Operation extends Fonction {

    
    public Table difference(Table t1, Table t2) throws Exception {
        System.out.println("Difference");
        
        Table inter = intersection(t1, t2);
        
        Object[][] dataInter = inter.getData();
        
        Object[][] data1 = t1.getData();
        
        Vector v = new Vector();
        for (int i = 0; i < data1.length; i++) {
            if(isInTable(data1[i], inter) == false) {
                v.add(data1[i]);
            }
        }

        Object[][] dataDiff = null;
        Table difference = null;
        Table[] tab = { t1, t2 };
        if(v.size() > 0) {
            
            dataDiff =getByvector(v);  
            
            difference = new Table(t1.getcolonne(), dataDiff);  
            difference.setisTableSelect(tab);
        }

        else {
            difference = new Table();
           
            Object[] colonne = {"Error"};
           
            Object[][] data = new Object[1][1];
           
            data[0][0] = "Aucun resultat";
           
            difference.setcolonne(colonne);
           
            difference.setData(data);
        }
        
        
        return difference;
    }

    public Table selection(Table table, String colonne, String operateur, String valeur) throws Exception {
        System.out.println("Selection");
        
        InputOutput in = new InputOutput();
        
        Object[] colonnes = table.getcolonne();
        
        Object[][] data = table.getData();
        
        int indCol = getIndColonne(colonne, table);
        
        Object[] typesCol = in.getTypesTableInFile(table.getNom());
        
        String typeColonne = typesCol[indCol].toString();

        Vector v = new Vector();

       
        for (int i = 0; i < data.length; i++) {
            
            String dataStr = (String) data[i][indCol];
            
            if(operateur.equals("=")) {
            
                if(typeColonne.equalsIgnoreCase("string") == true || typeColonne.equalsIgnoreCase("date") == true) {
            
                    if(dataStr.equals(valeur) == true) {
                        v.add(data[i]);   
                    }
                }
            
                else { 
                    if(Double.parseDouble(dataStr) == Double.parseDouble(valeur)) {
                        v.add(data[i]); 
                    }
                }
            }

            else if((operateur.equalsIgnoreCase("like")) == true) {
                if(data[i][indCol].toString().contains(valeur) == true) {
                    v.add(data[i]);
                }
            }
            
            else if(operateur.equals(">")) {
                if(Double.parseDouble(dataStr) > Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            else if(operateur.equals(">=")) {
                if(Double.parseDouble(dataStr) >= Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            else if(operateur.equals("<")) {
                if(Double.parseDouble(dataStr) < Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            else if(operateur.equals("<=")) {
                if(Double.parseDouble(dataStr) <= Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            
        }

        Object[][] donnee =getByvector(v);

        Table tab = new Table(colonnes, donnee);
        tab.setisTableSelect(table.getisTableSelect());

        return tab;
    }

    
    public Table produitCartesien(Table t1, Table t2) throws Exception {
        ///Produit nomColonne
        Object[] colonne1 = t1.getcolonne();
        
        Object[] colonne2 = t2.getcolonne();
        
        Object[] colonneProd = mergeObj(colonne1, colonne2);

        ///Produit donnee
        
        Object[][] data1 = t1.getData();
        Object[][] data2 = t2.getData();

        Object[][]obj1 = data1;
        Object[][]obj2 = data2;

        InputOutput in = new InputOutput();

        if(t1.getNom() != null) {
            Object[] type = in.getTypesTableInFile(t1.getNom());
            
            if(type[0].toString().equalsIgnoreCase("string") == false) {
                obj1 = fetchTo2D(data1);
                
                obj2 = fetchTo2D(data2);
            }
        }
        else {
            obj1 = fetchTo2D(data1);
            obj2 = fetchTo2D(data2);
        }
        int nbLignes = data1.length * obj2.length;  //maka nb ligne
        
        int nbCol = obj1[0].length + obj2[0].length;    //maka nb colonnes
       

        Object[][] rep = new Object[nbLignes][nbCol];

        int k2 = 0;
        int indLigneobj1 = 0;
        int indLigneobj2 = 0;

        for (int i = 0; i < nbLignes; i++) {
           

            if (indLigneobj2 >= obj2.length) {
                indLigneobj2 = 0;
                indLigneobj1++;
            }
            for (int k = 0; k < obj1[0].length; k++) {
                rep[i][k] = obj1[indLigneobj1][k];
                k2 = k;
            }

            int k3 = k2 + 1;
            for (int l = 0; l < obj2[0].length; l++) {
                rep[i][k3 + l] = obj2[indLigneobj2][l];
            }

            if (indLigneobj2 < obj2.length) {
               
                indLigneobj2++;
            }
        }

        return new Table(colonneProd, rep);
    }

    public Table projection(Object[] colonne, Object[][] data ,String[] operateurs) throws Exception {
        Vector v = new Vector();
        
        for (int i = 0; i < colonne.length; i++) { // selection colonne
        
            for (int j = 0; j < operateurs.length; j++) {
        
                if(colonne[i].toString().equalsIgnoreCase(operateurs[j])) {
                    v.add(i);
                }
            }
        }

        Object[] obj = new Object[v.size()];
        
        Object[] listInd = v.toArray();

        int ind = 0;
        for (int i = 0; i < colonne.length; i++) {
            int k = Integer.parseInt(listInd[ind].toString());
            if(i == k) {
                obj[ind] = colonne[i];
                // System.out.println(obj[ind]);
                ind++;
                i = 0;
            }
            
            if(ind >= v.size()) {
                break;
            } 
        }

        Object [][] donnee = new Object[data.length][v.size()];

        int indProj = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                // System.out.println("hellooo"); 
                //System.out.println(j);
                if(j == (int) listInd[indProj]) {
                    donnee[i][indProj] = data[i][j];
                    // System.out.println(data[i][j]);
                    indProj++;
                    j = 0;
                }

                if(indProj >= donnee[0].length) {
                    break;
                } 
            }
            indProj = 0;
        }

        return new Table(obj, donnee);
    }

    /*public void decrire(Object[] lo) {
        for (Object object : lo) {
            System.out.print(object+"  ");
        }
        System.out.print(" | ");
    }*/

    public Table intersection(Table t1, Table t2) throws Exception {
        System.out.println("Intersection");
        
        System.out.println();
        
        Table t = new Table();

        Table[] usedTab = { t1, t2 };
        t.setisTableSelect(usedTab);
        
        Object[][] data1 = t1.getData();
        
        Object[][] data2 = t2.getData();
        
        Vector v = new Vector();

        for (Object[] ligne1 : data1) {
            for (Object[] ligne2 : data2) {
                
                if(isDoublureString(ligne1, ligne2) == true) {
                    v.add(ligne1);
                }
            }
        }

       
        Object[][] inter =getByvector(v);

        t.setcolonne(t1.getcolonne());
        t.setData(inter);
        return t;
    }


    public Table union(Table t1, Table t2) throws Exception {
        System.out.println("Union");
        System.out.println();

        Table t = new Table();
        
        Object[][] data = merge2Obj(t1.getData(), t2.getData());
        
        Object[][] delDouble = updateMitovy(data);
        
        t.setcolonne(t1.getcolonne());
        
        t.setData(delDouble);

        Table[] tab = { t1, t2 };
        t.setisTableSelect(tab);
        
        return t;
    }

    public Object[] mergeObj(Object[] lo1, Object[] lo2) {

        int t1 = 0;
        int t2 = 0;

        if (lo1 == null) {
            t1 = 0;
        } else {
            t1 = lo1.length;
        }

        if (lo2 == null) {
            t2 = 0;
        } else {
            t2 = lo2.length;
        }

        Object[] rep = new Object[t1 + t2];
        int i = 0;
        if (lo1 != null) {
            for (Object Object : lo1) {
                rep[i] = Object;
                i++;
            }
        }

        if (lo2 != null) {
            for (Object Object : lo2) {
                rep[i] = Object;
                i++;
            }
        }

        return rep;
    }
}