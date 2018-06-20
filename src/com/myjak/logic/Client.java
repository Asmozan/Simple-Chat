package com.myjak.logic;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String userName;
    private String serverHost;
    private int serverPortUsed;

    public Client(String userName, String host, int portNumber){
        this.userName = userName;
        this.serverHost = host;
        this.serverPortUsed = portNumber;
    }

    public static String validateUsername(Scanner scanner)
    {
        String userName = scanner.nextLine();
        while(userName == null || userName.trim().equals("")){
            userName = scanner.nextLine();
            if(userName.trim().equals("")){
                System.out.println("Invalid username provided. Please try again:");
            }
        }
        return userName;
    }

    public void start(Scanner scan){
        try{
            Socket socket = new Socket(serverHost, serverPortUsed);
            Thread.sleep(1234);

            ServerThread serverThread = new ServerThread(socket, userName);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            while(serverAccessThread.isAlive()){
                if(scan.hasNextLine()){
                    serverThread.addNextMessage(scan.nextLine());
                }
            }
        }catch(IOException ex){
            System.err.println("Fatal Connection Error!");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
}