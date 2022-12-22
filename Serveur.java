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
public class Serveur {

    public static void main(String[] test) {

        final ServerSocket serverSocket;
        
        final Socket clientSocket;
        
        final BufferedReader in;
        
        final PrintWriter out;
        
        final Scanner sc = new Scanner(System.in);
        
        String iplocal = "localhost";
        int port = 1827;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("En mode serveur");
            
            
            System.out.println("port : " + port);
            
            clientSocket = serverSocket.accept();
            
            System.out.println("Connecte avec le client");
            
            out = new PrintWriter(clientSocket.getOutputStream());
            
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // envoi.start();

            Fonction f = new Fonction();

            Thread receive = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        String requete;
                        
                        requete = in.readLine();
                        
                                
                        OutputStream os = clientSocket.getOutputStream(); // send message au client

                        ObjectOutputStream message = new ObjectOutputStream(os); // pour l'envoie de l'objet

                        while (requete != null) {

                            try {
                                System.out.println("Client : " + requete);

                                Table relation = f.fetchRequest(requete);

                                

                                Object objet = relation;
                                //  a envoyé

                                message.writeObject(objet); // envoie de l'obj

                                System.out.println("Server : requete envoye");
                                 
                                 f.displayResult(relation);
                                //f.display(relation);

                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            requete = in.readLine();
                        }
                        // sortir de la boucle si le client a déconecté
                        System.out.println("Client deconnecté");
                        // fermer le flux et la session socket
                        out.close();
                        
                        clientSocket.close();
                        
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            receive.start();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}