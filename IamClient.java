package client;

import entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by HHB on 2022/11/16.
 */
public class IamClient {
    //为了规范，提前部署好日志的记录环境
    private static final Logger logger = LoggerFactory.getLogger(IamClient.class);

    public static void main(String[] args){
        IamClient huClient = new IamClient();
        Message message = (Message) huClient.send(new Message("我客户端想发送这一坨东西"),"127.0.0.1",2221);
        System.out.println("client receive message from server:" + message.getSendContent());
    }

    public Object send(Message message, String host, int port){
        try {
            //后期不就可以把这个host, port封装到配置文件中了嘛
            Socket socket = new Socket(host, port);
            ObjectOutputStream oo = new ObjectOutputStream(socket.getOutputStream());
            //通过输出流向服务器端发送请求信息
            oo.writeObject(message);
            //通过输入流获取服务器响应的信息
            ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
            return oi.readObject();//ClassNotFoundException e,这个错不捕获，oi.readObject()就报错
        }catch (IOException | ClassNotFoundException e){
            logger.error("occur exception:", e);
        }
        return null;
    }
}
