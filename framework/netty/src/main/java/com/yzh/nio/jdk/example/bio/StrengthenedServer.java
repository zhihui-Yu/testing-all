package com.yzh.nio.jdk.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class StrengthenedServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8881);

        while (true) {
            Socket socket = server.accept();
            new Thread(
                () -> {
                    try {
                        while (true) {
                            InputStream inputStream = socket.getInputStream();
                            byte[] b = new byte[1024];
                            int len;
                            StringBuffer sb = new StringBuffer();
                            //一次交互完成后，while循环过来，在此阻塞，即监听
                            while ((len = inputStream.read(b)) != -1) {
                                sb.append(new String(b, 0, len));
                                //单次交互结束标识，跳出监听
                                if (new String(b, 0, len).contains("over")) {
                                    break;
                                }
                            }
                            String content = sb.toString();
                            System.out.println("接收到客户端消息" + content.substring(0, content.length() - 4));

                            //往客户端发送数据
                            long nowtime = (new Date()).getTime();
                            byte[] bytes = (nowtime + "over").getBytes(StandardCharsets.UTF_8);
                            System.out.println("send: " + new String(bytes));
                            socket.getOutputStream().write(bytes);
                            socket.getOutputStream().flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            ).start();
        }
    }
}