package com.wzg.socketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerTest {

    public static void main(String[] args) {
        try {
            // 创建ServerSocketChannel -> ServerSocket
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 绑定一个端口:8000, 在服务器端监听
            ssc.socket().bind(new InetSocketAddress(8000));
            // 设置为非阻塞
            SelectableChannel  selectableChannel = ssc.configureBlocking(false);

            // 得到Selector对象
            Selector selector = Selector.open();
            // 注册 channel，并且指定感兴趣的事件是 Accept
            SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            ByteBuffer writeBuff = ByteBuffer.allocate(128);
            writeBuff.put("received".getBytes());
            writeBuff.flip();

            while (true) {
                int nReady = selector.select();
                System.out.println(nReady);
                /*如果返回的>0, 就获取到相关的selectionKey集合
                 1. 如果返回的>0, 表示已经获取到关注的事件了
                 2. selector.selectedKeys()返回关注事件的集合
                   通过 selectionKeys反向获取通道
                 */
                Set<SelectionKey> keys = selector.selectedKeys();
                // 遍历 Set<SelectionKey>, 使用迭代器遍历(iterator)
                Iterator<SelectionKey> it = keys.iterator();
                // 无事件发生
                while (it.hasNext()) {
                    // 获取到selectionKey
                    SelectionKey key = it.next();
                    // 手动从集合中移除当前的selectionKey, 防止重复操作
                    it.remove();
                    // 根据key对应的通道发生的事件做相应的处理
                    if (key.isAcceptable()) {// 如果是OP_ACCEPT(有新的客户端连接)
                        // 创建新的连接，并且把连接注册到selector上，而且，
                        // 声明这个channel只对读操作感兴趣。
                        // 为该客户端生成一个SocketChannel
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        // 将当前的 socketChannel 注册到 selector, 关注事件为OP_READ,
                        // 同时给socketChannel关联一个Buffer
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) { // 发生OP_READ
                        // 通过key反向获取到对应的channel
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuff.clear();
                        // 获取到该channel关联的buffer
                        socketChannel.read(readBuff);
                        readBuff.flip();
                        System.out.println("received : " + new String(readBuff.array()));
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        writeBuff.rewind();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(writeBuff);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
