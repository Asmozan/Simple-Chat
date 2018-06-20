package com.myjak.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    private int port;
    private List<ClientThread> clients;

    public Server(int port)
    {
        this.port = port;
    }

    List<ClientThread> getClients()
    {
        return clients;
    }

    public void start()
    {
        clients = new ArrayList<>();
        ServerSocket serverSocket;
        try
        {
            serverSocket = new ServerSocket(port);
            acceptClients(serverSocket);
        } catch (IOException e)
        {
            System.err.println("Could not listen on port: " + port);
            System.exit(54093);
        }
    }

    private void acceptClients(ServerSocket serverSocket)
    {
        System.out.println("Server started on port: " + serverSocket.getLocalSocketAddress());
        while (true)
        {
            try
            {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted client on: " + socket.getRemoteSocketAddress());
                ClientThread client = new ClientThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException ex)
            {
                System.out.println("Accept failed on: " + port);
            }
        }
    }
}