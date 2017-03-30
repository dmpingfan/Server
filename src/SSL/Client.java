package SSL;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;

/**
 * Created by TOM on 2017/3/28.
 */
public class Client extends Thread{
    private static String ip;
    private static int port;
    private SSLSocket sslSocket;
    public Client(String ip, int port)
    {
        this.ip=ip;
        this.port=port;
    }
    /**
     * 启动客户端程序
     *
     * @param args
     */
    public static void main(String[] args) {
        while (true){
            Client client = new Client("localhost", 8002);
            client.start();
        }
    }

    /**
     * 通过ssl socket与服务端进行连接,并且发送一个消息
     */
    @Override
    public void run() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);

            bos.write("Client Message".getBytes());
            bos.flush();

            byte[] buffer = new byte[20];
            bis.read(buffer);
            System.out.println(new String(buffer));

            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * <ul>
     * <li>ssl连接的重点:</li>
     * <li>初始化SSLSocket</li>
     * <li>导入客户端私钥KeyStore，导入客户端受信任的KeyStore(服务端的证书)</li>
     * </ul>
     */
    @Override
    public void start() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream("keytool/kclient.keystore"), "aaaaaa".toCharArray());
            tks.load(new FileInputStream("keytool/tclient.keystore"), "aaaaaa".toCharArray());

            kmf.init(ks, "aaaaaa".toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(this.ip, this.port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
