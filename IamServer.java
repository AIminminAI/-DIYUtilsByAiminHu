package server;


import entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author HHB
 * @date 2022/11/16
 */
public class IamServer {
    //为了规范，提前部署好日志的记录环境
    private static final Logger logger = LoggerFactory.getLogger(IamServer.class);

    public static void main(String[] args) {
        IamServer server = new IamServer();
        //stub是我自己想把网络细节封装起来，对外提供的一个stub方法，这跟Version01~Version06一样
        //指定服务端暴露的端口为2221
        server.stub(2221);
    }

    //你封装成啥样，你得让人家知道吧
    public void stub(int port){
        Socket socket;
        try {
            //后期不就可以把这个port封装到配置文件中了嘛
            ServerSocket serverSocket = new ServerSocket(port);
            //开始经典的BIO
            //ServerSocket的accept()方法是阻塞方法，accept()方法被调用时，也就是服务端在等待客户端的连接请求时会卡，直到服务端收到客户端连接时才会继续往下
            while((socket = serverSocket.accept()) != null){
                logger.info("如果程序走到这里，打印出这个括号中的话就说明客户端的bind、listen、accept等步骤已经进行完了，客户端已经和服务端建立起连接了");
                try {
                    //下来就是客户端或者客户端代理通过输出流发送请求信息，服务器通过输入流读取客户端发送来的请求信息。同样的服务端通过输出流向客户端发送响应消息。一般都是固定写法：
                    ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message)oi.readObject();
                    logger.info("server receive message:" + message.getSendContent());
                    message.setSendContent("服务端发的：客户端你个瓜娃，快给我发，慢的很");

                    ObjectOutputStream oo = new ObjectOutputStream(socket.getOutputStream());
                    oo.writeObject(message);
                    oo.flush();
                }catch (IOException | ClassNotFoundException e){
                    logger.error("occur exception:", e);
                }
            }
        }catch (IOException e){
            logger.error("occur IOexception:", e);
        }
    }

}
