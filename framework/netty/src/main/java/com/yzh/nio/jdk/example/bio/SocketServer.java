package com.yzh.nio.jdk.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author simple
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8881);
        while (true) {
            new Thread(() -> {
                Socket accept = null;
                try {
                    accept = serverSocket.accept();
                    final InputStream stream = accept.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    StringBuilder message = new StringBuilder();
                    while ((len = stream.read(bytes)) != -1) {
                        String str = new String(bytes, 0, len, UTF_8);
                        message.append(str);
                        if (str.indexOf("end") > 0) break;
                    }
                    System.out.println("get message is: " + message);
                    stream.close();
                    accept.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
