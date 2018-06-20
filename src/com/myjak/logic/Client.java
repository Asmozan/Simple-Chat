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

    public void startClient(Scanner scan){
        try{
            Socket socket = new Socket(serverHost, serverPortUsed);
            Thread.sleep(1000); // waiting for network communicating.

            ServerThread serverThread = new ServerThread(socket, userName);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            while(serverAccessThread.isAlive()){
                if(scan.hasNextLine()){
                    serverThread.addNextMessage(scan.nextLine());
                }
                // NOTE: scan.hasNextLine waits input (in the other words block this thread's process).
                // NOTE: If you use buffered reader or something else not waiting way,
                // NOTE: I recommends write waiting short time like following.
                // else {
                //    Thread.sleep(200);
                // }
            }
        }catch(IOException ex){
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
}