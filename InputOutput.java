package inputOutput;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import fonction.Fonction;

public class InputOutput {

    
    //ITU
    public static String describeTable = "D:/Etude ITU/Fianarana L2/Mr Naina/Projet/SGBD tena izy/Serveur/describeTable/";
    
    public static String donneeTable = "D:/Etude ITU/Fianarana L2/Mr Naina/Projet/SGBD tena izy/Serveur/Table/";

    /////////////////////////////////
    public int countData1(Object o) throws Exception {
        int n = 0;
        Scanner scan = new Scanner(new File("describeTable." + o.getClass().getSimpleName()));
        while (scan.hasNext() == true) {
            while (scan.hasNext("-") == false) {
                scan.next();
            }
            n++;
            scan.next();
        }
        scan.close();

        return n;
    }

    public int countAttribut1(Object o) throws Exception {
        int n = 0;
        Scanner scan = new Scanner(new File("describeTable." + o.getClass().getSimpleName()));
        while (scan.hasNext("-") == false) {
            n++;
            scan.next();
        }
        scan.close();

        return n;
    }

    public Object[][] lire1(Object o) throws Exception {
        Object[][] data = new Object[countData1(o)][countAttribut1(o)];
        Scanner scan = new Scanner(new File("describeTable." + o.getClass().getSimpleName()));
        int i = 0;
        int j = 0;
        while (scan.hasNext() == true) {
            while (scan.hasNext("-") == false) {
                data[i][j] = scan.next();
                j++;
            }
            i++;
            j = 0;
            scan.next();
        }
        scan.close();
        return data;
    }

    ////////////////////////////////
    
    public void insertData(Object obj, String nomTable) throws Exception { 
        
        File file = new File("Table");  //file Table
        
        FileWriter write = new FileWriter(new File("Table/" + nomTable +".txt"), true);    //boolean:indiquant s'il faut ou non ajouter les données écrites.
        
        BufferedWriter buff = new BufferedWriter(write); //tamponé

        Field[] listeAttribut = obj.getClass().getDeclaredFields();
        for (Field field : listeAttribut) {
            
            String in = field.get(obj).toString() + ";;";
            
            //f.setAccessible(true);
            /*if (w.equals("") || w.equals(" ")) {
                w = "0";
            }*/
            buff.write(in);
        }
        buff.newLine();
        
        buff.write("-----");
        
        buff.newLine();
        
        buff.close();
        
        System.out.println("insertion reussi");                                               
    }

    //****************************************************************************************
    public void insertData(String nomTable, String [] values) throws Exception {        //inserer anaty fichier txt
        File file = new File("Table");
        
        //file.mkdir();
        
        FileWriter write = new FileWriter(new File("Table/" + nomTable +".txt"), true);
        
        BufferedWriter buff = new BufferedWriter(write);

        for (String s : values) {
            String in = s + ";;";
            /*if (w.equals("") || w.equals(" ")) {
                w = "0";
            }*/
            buff.write(in);
        }
        buff.newLine();
        
        buff.write("-----");
        
        buff.newLine();

        buff.close();
        
        System.out.println("insertion reussi");
    }
    //******************************************************************************************** */

    public Object[] getTypesTableInFile (String nomTable) throws Exception {
        
        
        String repertoire = describeTable+ nomTable + ".txt";
        
        File file = new File(repertoire);
        
        Vector v = new Vector();
        
        if(file.exists() == true) {
            Scanner scanner = new Scanner(file);
            
            Object[] colonne = new Object[2];
            
            while (scanner.hasNext() == true) {
                
                while(scanner.hasNext("-----") == false) {
                    String s = scanner.next();
                    
                    String[] data = s.split(";;");
                    
                    colonne[0] = data[0]; //description sy type
                    
                    colonne[1] = data[1];
                    
                    v.add(colonne[1]);
                }
                colonne = new Object[2];
                scanner.next();
            }
        }
        else {
            throw new Exception("le fichier "+repertoire+ " est introuvable");
        }
        

        return v.toArray();
    }
    //*********************************************************************************** */
    
    public boolean isInDatabase(String nomTable) throws Exception {
        // String repertoire = "database/table." + nomTable;
        // String location = "Home";
        // String repertoire = "E:/JAVA/S3/BD Relationnelle/database/table." + nomTable;
        String repertoire = donneeTable+ nomTable + ".txt";
        File file = new File(repertoire);
        if(file.exists() == true) {
            return true;}
        
        throw new Exception("le fichier "+repertoire+ " est introuvable");
        // return false;
    }
//***************************************************************************** */
    
    public boolean checkColonne(String colonne, String nomTable) throws Exception {
        
        Object[] colonnes = getEnteteTable(nomTable);   //maka ny colonne ao am table
        
        for (Object obj : colonnes) {
        
            if(colonne.equalsIgnoreCase(obj.toString()) == true) {
                return true;
            }   
        }
        
        throw new Exception(" la colonne : "+colonne+ " n`existe pas");
    }
    //********************************************************************* */
    
    public void insertDescribe(Object[] lo, String nomTable) throws Exception {
        File file = new File("description");
        
        
        FileWriter write = new FileWriter(new File("describeTable/" + nomTable +".txt"), true);
        
        BufferedWriter buff = new BufferedWriter(write);

        Field[] listeAttribut = lo[0].getClass().getDeclaredFields();
        
        for (Field field : listeAttribut) {
            field.setAccessible(true);
            
            String s = field.getName() + ";;" + field.getType().getSimpleName();
            buff.write(s);
            
            buff.newLine();
            
            buff.write("-----");
            
            buff.newLine();
        }

        buff.close();
    }
    //*********************************************************************** */
    
    public void insertDescribe(String nomTable, String[][] colType) throws Exception {
        try {
            if (isInDatabase(nomTable) == true)
                throw new Exception(nomTable + " existe deja dans la base de donnees");
        
        } catch (Exception e) {
            File file = new File("describeTable/");
            //file.createNewFile();
         
         
            FileWriter write = new FileWriter(new File("describeTable/" + nomTable +".txt"), false);
         
            BufferedWriter buff = new BufferedWriter(write);

            for (String[] colonnes : colType) {
                String s = colonnes[0] + ";;" + colonnes[1];
                
                buff.write(s);
                
                buff.newLine();
                
                buff.write("-----");
                
                buff.newLine();
            }
            buff.close();
        }
    }

    public void insertData(Object[] lo, String nomTable) throws Exception {
        File file = new File("Table");
        
        FileWriter write = new FileWriter(new File("Table/" + nomTable +".txt"), true);
        BufferedWriter buff = new BufferedWriter(write);

        int i = 0;
        for (Object o : lo) {
            Field[] listeAttribut = o.getClass().getDeclaredFields();
            for (Field field : listeAttribut) {
                field.setAccessible(true);
                String s = field.get(o).toString() + ";;";
                
                buff.write(s);
            }
            
            buff.newLine();
            
            buff.write("-----");
            
            buff.newLine();
        }
        buff.close();
    }
    //************************************************************************* */
    
    public Object[][] lire(String nomTable) throws Exception {  //[i][i] ligne i colonne i
        Fonction fonction = new Fonction();
        
        Vector data = new Vector();
        
        Vector v = new Vector();
        
        String repertoire = donneeTable + nomTable+".txt";
        
        Scanner scan = new Scanner(new File(repertoire));
        
        while (scan.hasNext() == true) {
            
            while (scan.hasNext("-----") == false) {
                String bloc = scan.next();
                
                String [] d = bloc.split(";;");
                
                for (String string : d) {
                    
                    data.add(string);
                }
            }
            
            v.add(data.toArray());
            
            data = new Vector();
            scan.next();
        }
        Object[][] rep = fonction.getByvector(v);
        
        scan.close();
        
        return rep;
    }
    //*********************************************************************************** */
    
    public Object[] getEnteteTable (String nomTable) throws Exception {
        
        String repertoire = describeTable+ nomTable + ".txt";
        
        File file = new File(repertoire);
        
        Vector v = new Vector();
        
        if(file.exists() == true) {
            Scanner scan = new Scanner(file);
            
            Object[] colonne = new Object[2];
            
            while (scan.hasNext() == true) {
               
                while(scan.hasNext("-----") == false) {
                    String bloc = scan.next();
                    
                    String[] data = bloc.split(";;");
                    
                    colonne[0] = data[0]; 
                    colonne[1] = data[1];
                    
                    v.add(colonne[0]);
                }
                colonne = new Object[2];
                scan.next();
            }
        }
        else {
            throw new Exception("le fichier " +repertoire+ " est introuvable");
        }
        

        return v.toArray();
    }

}