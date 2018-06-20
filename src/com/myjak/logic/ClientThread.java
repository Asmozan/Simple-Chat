package com.myjak.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private Server server;

    ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try{
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner inputStream = new Scanner(socket.getInputStream());

            communicationStart(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void communicationStart(Scanner inputStream)
    {
        while(socket.isConnected()){
            if(inputStream.hasNextLine()){
                String input = inputStream.nextLine();
                for(ClientThread client : server.getClients()){
                    PrintWriter clientMessage = client.getWriter();
                    if(clientMessage != null){
                        clientMessage.write(input + "\r\n");
                        clientMessage.flush();
                    }
                }
            }
        }
    }
}