package com.ypl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author 叶佩林
 * @Date 2021/10/21 23:33
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //设置目标服务器的地址，端口号
        Socket socket = new Socket("127.0.0.1", 8081);
        //获取网络字节输出流对象
        OutputStream oS = socket.getOutputStream();
        //使用write给服务器发送数据
        oS.write("".getBytes());
        //获取网络字节输入流对象
        //使用read，读取服务器回写数据
        //释放资源
        socket.close();
    }
}
