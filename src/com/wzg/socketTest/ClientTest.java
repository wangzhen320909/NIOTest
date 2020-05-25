package com.wzg.socketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest {

    final static int port = 7788;
    final static String address = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        socket = new Socket(address, port);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        while (true){
            out.println("接受到客户端的请求数据： ");
            out.println("接受到客户端的请求数据1111： ");
            String response = bufferedReader.readLine();
            System.out.println("Client: " + response);

        }

    }

}
