package com.yzh.nio.jdk.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author simple
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        // 基于stream 的通讯
        Socket socket = new Socket("127.0.0.1", 8881);

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            String line;
            try {
                while (!(line = sc.nextLine()).equals("#")) {
                    final OutputStream stream;
                    stream = socket.getOutputStream();
                    stream.write(line.getBytes(StandardCharsets.UTF_8));
                    stream.flush();
                }
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                while (socket.isConnected()) {
                    InputStream inputStream = socket.getInputStream();
                    int sum = inputStream.read(byteBuffer.array());
                    System.out.println("receive: " + new String(byteBuffer.array(), 0, sum));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
