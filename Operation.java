package fonction;

import java.util.Vector;

import syntaxe.Syntaxe;
import inputOutput.InputOutput;
import objets.*;

import java.lang.reflect.*;

public class Operation extends Fonction {

    
    
    public Table projection(Object[] entete, Object[][] data ,String[] selections) throws Exception {
        Vector v = new Vector();
        
        for (int i = 0; i < entete.length; i++) { // selection entete
            
            for (int j = 0; j < selections.length; j++) {
               
                if(entete[i].toString().equalsIgnoreCase(selections[j])) {
                    v.add(i);
                }
            }
        }

        Object[] obj = new Object[v.size()];
        
        Object[] listInd = v.toArray();

        int ind = 0;
        for (int i = 0; i < entete.length; i++) {
            int k = Integer.parseInt(listInd[ind].toString());
            if(i == k) {
                obj[ind] = entete[i];
                
                ind++;
                i = 0;
            }
            
            if(ind >= v.size()) {
                break;
            } 
        }

        Object [][] dataSelect = new Object[data.length][v.size()];

        int indProj = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                
                if(j == (int) listInd[indProj]) {
                    dataSelect[i][indProj] = data[i][j];
                    // System.out.println(data[i][j]);
                    indProj++;
                    j = 0;
                }

                if(indProj >= dataSelect[0].length) {
                    break;
                } 
            }
            indProj = 0;
        }

        return new Table(obj, dataSelect);
    }


    public Table intersection(Table le1, Table le2) throws Exception {
        System.out.println("Intersection");
        
        System.out.println();
        
        Table u = new Table();
        
        Object[][] data1 = le1.getData();
        
        Object[][] data2 = le2.getData();
        
        Vector interVect = new Vector();

        for (Object[] ligne1 : data1) {
            for (Object[] ligne2 : data2) {

                if(checkStringMverina (ligne1, ligne2) == true) {
                    interVect.add(ligne1);
                }
            }
        }

        
        Object[][] inter =getByvector(interVect);

        
        u.setEntete(le1.getEntete());
        
        u.setData(inter);
        
        return u;
    }


    public Table union(Table le1, Table le2) throws Exception {
        System.out.println("Union");
        System.out.println();

        
        Table u = new Table();
        
        Object[][] data = listerObjetA2Dim(le1.getData(), le2.getData());
        
        Object[][] delDouble = updateMiverina(data);
        
        u.setEntete(le1.getEntete());
        
        u.setData(delDouble);
        
        return u;
    }

    public Table selection(Table table, String colonne, String syntaxe, String valeur) throws Exception {
        
        InputOutput e = new InputOutput();
        
        Object[] colonnes = table.getEntete();
        
        Object[][] data = table.getData();
        
        int indCol = getIndColonne(colonne, table);
        
        Object[] typesCol = e.getTypesTableInFile(table.getNom());
        
        String typeColonne = typesCol[indCol].toString();

        Vector v = new Vector();

        
        for (int i = 0; i < data.length; i++) {
            
            
            String dataStr = (String) data[i][indCol];
            
            if(syntaxe.equals("=")) {
            
                if(typeColonne.equalsIgnoreCase("string") == true || typeColonne.equalsIgnoreCase("date") == true) {
            
                    if(dataStr.equals(valeur) == true) {
            
                        v.add(data[i]);   
                    }
                }
            
                else { //int / double / float
                    if(Double.parseDouble(dataStr) == Double.parseDouble(valeur)) {
                        v.add(data[i]); 
                    }
                }
            }
            
            else if(syntaxe.equals(">")) {
                if(Double.parseDouble(dataStr) > Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            
            else if(syntaxe.equals(">=")) {
                if(Double.parseDouble(dataStr) >= Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            
            else if(syntaxe.equals("<")) {
                if(Double.parseDouble(dataStr) < Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            
            else if(syntaxe.equals("<=")) {
                if(Double.parseDouble(dataStr) <= Double.parseDouble(valeur)) {
                    v.add(data[i]); 
                }
            }
            
            else if((syntaxe.equalsIgnoreCase("like")) == true) {
                if(data[i][indCol].toString().contains(valeur) == true) {
                    v.add(data[i]);
                }
            }
            // else throw new Exception("syntaxe invalide : "+syntaxe); 
        }

        Object[][] dataSelect =getByvector(v);
        return new Table(colonnes, dataSelect);
    }
}