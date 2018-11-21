package com.myth.socket_learn;

import java.net.Socket;
import java.util.Date;


public class IOClient {
    public static void main(String[] args) {
        // Socket编程,启动一个客户端.用来吊起客户端.
        new Thread(()->{
            try{
                // 创建一个客户端的Socket,并且绑定到指定端口.实际应用中应该是动态的.
                Socket socket = new Socket("localhost",8000);
                // 创建一个死循环,先ServerSocket发送消息.
                while (true){
                    try{
                        // 向ServerSocket发送消息.
                        socket.getOutputStream().write((new Date() + "  hello server").getBytes());
                        // 线程休眠2秒
                        Thread.sleep(2000);
                    }catch (Exception e){
                        System.out.println("01"+e.getMessage());
                    }
                }
            }catch (Exception e){
                System.out.println("Client..." + e.getMessage());
            }
        }).start();

    }

}
