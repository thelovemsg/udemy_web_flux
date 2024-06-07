package com.reactivespring.os;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DateServer {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(6013);

        while (true) {
            System.out.println("Listening...");
            Socket client = server.accept();
            PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
            System.out.println("New client is connected...");

            pout.println(new java.util.Date());

            client.close();
        }

    }
}
