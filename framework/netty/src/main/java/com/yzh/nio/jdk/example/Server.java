package com.yzh.nio.jdk.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author simple
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("server started...");
        new Server().start();
    }

    private final ByteBuffer readBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    private final ByteBuffer sendBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    String str;
    private Selector selector;
    private Selector readSelector;

    public void start() throws IOException, InterruptedException {
        // 打开服务器套接字通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        ssc.configureBlocking(false);
        // 进行服务的绑定
        ssc.bind(new InetSocketAddress("localhost", 8001));

        // 通过open()方法找到Selector
        selector = Selector.open();
        readSelector = Selector.open();

        // 注册到selector，等待连接
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) {
                    try {
                        SelectionKey key = keyIterator.next();
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isAcceptable()) {
                            accept(key);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    } finally {
                        keyIterator.remove(); //该事件已经处理，可以丢弃
                    }
                }
            }
        }).start();

    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("send: " + str);

        sendBuffer.clear();
        sendBuffer.put(str.getBytes());
        sendBuffer.flip();
        channel.write(sendBuffer);
        channel.register(readSelector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        this.readBuffer.clear();
        // Attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);
        } catch (IOException e) {
            // The remote forcibly closed the connection, cancel
            // the selection key and close the channel.
            key.cancel();
            socketChannel.close();
            return;
        }

        str = new String(readBuffer.array(), 0, numRead);
        System.out.println("receive: " + str);

        socketChannel.register(readSelector, SelectionKey.OP_WRITE);
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(readSelector, SelectionKey.OP_READ);
        System.out.println("a new client connected " + clientChannel.getRemoteAddress());

        new Thread(this::reactor).start();
    }

    private void reactor() {
        while (true) {
            try {
                readSelector.select();
                Set<SelectionKey> keys = readSelector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                    keyIterator.remove(); //该事件已经处理，可以丢弃
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}