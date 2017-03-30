package SSL;

import Config.Config;
import Json.RegisterRequest;
import Json.RegisterResponse;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.security.KeyStore;

/**
 * Created by TOM on 2017/3/7.
 */
public class TestSSLSocketClient {
    private static String kpath = Config.GetValueByKey("conf/server.properties","kcpath");
    private static String tpath = Config.GetValueByKey("conf/server.properties","tcpath");
    private static char[] password = Config.GetValueByKey("conf/server.properties","clientpwd").toCharArray();

    public static void main(String[] args) {
        run();
    }
    /**
     * @param
     */
    public static void run() {
        SSLContext context = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(kpath), password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            KeyManager [] km = kmf.getKeyManagers();
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(new FileInputStream(tpath), password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(km, tm, null);
        } catch (Exception e) {
            e.printStackTrace();  //捕获异常
        }
        SSLSocketFactory ssf = context.getSocketFactory();
        try {
            SSLSocket ss = (SSLSocket) ssf.createSocket("localhost", 8000);
            /**
             * 此处不是太明白：三个方法具体什么作用
             */
            //ss.setNeedClientAuth(true);
            //ss.setUseClientMode(true);
            //ss.setWantClientAuth(true);
            System.out.println("客户端就绪。");
            ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
            RegisterRequest registerRequest=new RegisterRequest("Register", "1", "1");
            String s=registerRequest.toString();
            System.out.println(s);
            os.writeObject(s);
            ObjectInputStream br = new ObjectInputStream(ss.getInputStream());
            try {
                String res= br.readObject().toString();
                System.out.println(res);
                RegisterResponse registerResponse=new RegisterResponse(res);
                System.out.println(registerResponse.getCommand());
                System.out.println(registerResponse.getErrorMsg());
                System.out.println(registerResponse.getResult());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            br.close();
            ss.close();
            os.close();
            System.out.println("客户端测试ok");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}