package com.myth.socket_learn;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的IO模型通信.
 */
public class IOServer {

    public static void main(String[] args) {
        // 创建一个新的线程,用于启动服务进程.(此进程个人感觉可省略.)
        new Thread(()->{
            ServerSocket serverSocket = null;
            try {
                // 创建一个服务器Socket,并且绑定到指定的端口
                serverSocket = new ServerSocket(8000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true){
                try {
                    // 调用ServerSocket的accept方法.该方法是阻塞的.没有客户端链接的时候,一致阻塞在这里.
                    Socket socket = serverSocket.accept();
                    // 当接受到新的请求的时候,服务端就会创建一个Socket链接,该连接和客户端是11对应的.
                    new Thread(()->{
                        try {
                            // 读取客户端传递过来的数据.
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // (3) 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }).start();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();

    }

}
