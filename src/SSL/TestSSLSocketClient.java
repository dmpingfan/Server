package SSL;

import Config.Config;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.security.KeyStore;

/**
 * Created by TOM on 2017/3/7.
 */
public class TestSSLSocketClient {
    private static String kpath = Config.GetValueByKey("conf/server.properties","kcpath");
    private static String tpath = Config.GetValueByKey("conf/server.properties","tcpath");
    private static char[] password = Config.GetValueByKey("conf/server.properties","clientpwd").toCharArray();

    /**
     * @param args
     */
    public static void main(String[] args) {
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
            ObjectInputStream br = new ObjectInputStream(ss.getInputStream());
            try {
                System.out.println(br.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            br.close();
            ss.close();
            System.out.println("客户端测试ok");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}