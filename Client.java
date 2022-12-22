package socket;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.rmi.server.*;

import fonction.Fonction;
import objets.*;
/*
 * www.codeurjava.com
 */
public class Client {

    public static void main(String[] args) {
        final Socket clientSocket;
        
        final BufferedReader in;
        
        final PrintWriter out;
        
        final Scanner sc = new Scanner(System.in);// pour lire à partir du clavier

        String ipLocal = "localhost";
        
        int port = 1827;

        try {
            /*
             * les informations du serveur ( port et adresse IP ou nom d'hote
             * 127.0.0.1 est l'adresse local de la machine
             */
            System.out.println("En mode client");
            
            clientSocket = new Socket(ipLocal, port);
            
            System.out.println("connecte avec le serveur");

            // flux pour envoyer
            out = new PrintWriter(clientSocket.getOutputStream());
            // flux pour recevoir
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread envoyer = new Thread(new Runnable() {
                String message;

                @Override
                public void run() {
                    while (true) {
                        message = sc.nextLine();
                        
                        out.println(message);
                        
                        out.flush();
                    }
                }
            });
            envoyer.start(); 

            Thread recevoir = new Thread(new Runnable() {
                
                
                Fonction f = new Fonction();

                @Override
                public void run() {
                    try {
                        
                        InputStream is = clientSocket.getInputStream(); //lire

                        ObjectInputStream objetIn = new ObjectInputStream(is);

                        while (objetIn != null) {
                            try {
                                // System.out.println("");
                                Object table  = objetIn.readObject(); //reader

                                

                                Table relation = (Table) table;

                                f.displayResult(relation);

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            
                        }

                        System.out.println("Serveur deconnecté");
                        
                        out.close();
                        
                        objetIn.close();
                        
                        clientSocket.close();
                    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}