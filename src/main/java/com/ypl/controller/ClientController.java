package com.ypl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Author 叶佩林
 * @Date 2021/10/22 0:01
 * @Version 1.0
 */
@Controller
public class ClientController {
    @RequestMapping(value = "/fileUpLoads", method = RequestMethod.GET)
    @ResponseBody
    public String fileUpLoad(MultipartFile file, HttpSession session) throws IOException {
        String s = null;
        Socket socket = null;
        BufferedInputStream fileIn = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 创建客户端Socket,链接服务器
            socket = new Socket("127.0.0.1", 8082);
            System.out.println("====================客户端已连接====================");
            // 获取Socket流中的输出流,功能:用来把数据写到服务器
            out = socket.getOutputStream();
            // 创建字节输入流,功能:用来读取数据源(图片)的字节
            fileIn = new BufferedInputStream(new FileInputStream("C:\\Users\\叶佩林\\Pictures\\Cache_3f3049c62a57a96c..jpg"));

//            fileIn = new BufferedInputStream(fileInputStream);
            // 把图片数据写到Socket的输出流中(把数据传给服务器)
            byte[] buffer = new byte[1024];
            int len = -1;
            // read返回值是读入缓冲区的字节总数
            while ((len = fileIn.read(buffer)) != -1) {
                // 把数据写到Socket的输出流中
                out.write(buffer, 0, len);
            }
            // 客户端发送数据完毕,结束Socket输出流的写入操作,告知服务器
            socket.shutdownOutput();
            // ====================反馈信息====================
            // 获取Socket的输入流,作用:读取反馈UUID信息
            in = socket.getInputStream();

            // 读反馈信息
            byte[] info = new byte[1024];
            // 把反馈信息存储到info数组中,并记录字节个数
            int length = in.read(info);
            // 显示反馈结果
            s = new String(info, 0, length);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            // 关闭流
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }
}
