package com.example.undertow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author simple
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        Socket socket = channel.socket();
        socket.connect(new InetSocketAddress("localhost", 8889));
    }
}
