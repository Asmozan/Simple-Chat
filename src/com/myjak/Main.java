package com.myjak;

import com.myjak.logic.Client;
import com.myjak.logic.Server;

import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        int portNumber = 4444;
        String host = "localhost";

        Scanner input = new Scanner(System.in);

        while (true)
        {
            String choice = input.nextLine();
            switch (choice)
            {
                case "S":
                {
                    Server server = new Server(portNumber);
                    server.start();
                    break;
                }
                case "C":
                {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Please input username:");
                    String userName = Client.validateUsername(scanner);
                    Client client = new Client(userName, host, portNumber);
                    client.start(scanner);
                    break;
                }
                default:
                    System.out.println("Bad input provided! Try Again!");
            }
        }
    }
}