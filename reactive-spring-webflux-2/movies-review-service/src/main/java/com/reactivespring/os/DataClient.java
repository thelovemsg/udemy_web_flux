package com.reactivespring.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.56.1", 6013);

        InputStream in = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line = null;
        while ((line = br.readLine()) != null)
            System.out.println(line);

        socket.close();
    }
}
