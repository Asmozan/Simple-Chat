package com.myjak.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable
{
    private Socket socket;
    private String userName;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    ServerThread(Socket socket, String userName)
    {
        this.socket = socket;
        this.userName = userName;
        messagesToSend = new LinkedList<>();
    }

    void addNextMessage(String message)
    {
        synchronized (messagesToSend)
        {
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run()
    {
        System.out.println("Welcome " + userName + "!");
        System.out.println("Local Port:" + socket.getLocalPort());
        System.out.println("Server: " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        try
        {
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverStream = new Scanner(serverInStream);

            while (socket.isConnected())
            {
                if (serverInStream.available() > 0)
                {
                    if (serverStream.hasNextLine())
                    {
                        System.out.println(serverStream.nextLine());
                    }
                }
                if (hasMessages)
                {
                    String nextMessage;
                    synchronized (messagesToSend)
                    {
                        nextMessage = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    serverOut.println(userName + " > " + nextMessage);
                    serverOut.flush();
                }
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}