package SSL;

import Config.Config;
import Json.RegisterRequest;
import Json.RegisterResponse;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Created by TOM on 2017/3/7.
 */
public class TestSSLSocketServer {
    private static String kpath =  Config.GetValueByKey("conf/server.properties","kspath");
    private static String tpath =  Config.GetValueByKey("conf/server.properties","kspath");
    private static char[] password = Config.GetValueByKey("conf/server.properties","serverpwd").toCharArray();
    public static void main(String[] args) {
        boolean flag = true;
        SSLContext context = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(kpath), password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            KeyManager[] km = kmf.getKeyManagers();
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(new FileInputStream(tpath), password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(km, tm, null);
        } catch (Exception e) {
            e.printStackTrace();   //捕获异常
        }
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) context.getServerSocketFactory();
        try {
            SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(8000);
            /**
             * 此处不是太明白：三个方法具体什么作用
             */
            //ss.setNeedClientAuth(true);
            // 控制接受的服务器模式SSLSocket是否将在开始时配置为要求客户端验证。
            //ss.setUseClientMode(true);
            //控制接受的连接是以（默认的）SSL 服务器模式还是在 SSL 客户端模式工作。
            //ss.setWantClientAuth(true);
            //  控制 accept 服务器模式的 SSLSockets 是否将在开始时配置为请求 客户端验证。
            System.out.println("等待客户点连接。。。");
            while (flag) {
                Socket s = ss.accept();
                System.out.println("接收到客户端连接");

                ObjectInputStream br = new ObjectInputStream(s.getInputStream());
                try {
                    String res= br.readObject().toString();
                    RegisterRequest registerRequest=new RegisterRequest(res);
                    System.out.println(res);
                    System.out.println(registerRequest.getCommand());
                    System.out.println(registerRequest.getId());
                    System.out.println(registerRequest.getPasswd());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                RegisterResponse registerResponse=new RegisterResponse("Register", "00", "注册成功");
                String s1=registerResponse.toString();
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                os.writeObject(s1);
                os.flush();
                os.close();
                System.out.println();
                s.close();
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}