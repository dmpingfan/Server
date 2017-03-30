package SSL;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Created by TOM on 2017/3/28.
 */
public class Server extends Thread{

    private SSLServerSocket     serverSocket;
    private int port;
    public Server(int port)
    {
        this.port=port;
    }
    /**
     * 启动程序
     *
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server(8002);
        server.start();
        server.run();
    }

    /**
     * <ul>
     * <li>听SSL Server Socket</li>
     * <li> 由于该程序不是演示Socket监听，所以简单采用单线程形式，并且仅仅接受客户端的消息，并且返回客户端指定消息</li>
     * </ul>
     */
    @Override
    public void run() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        while (true) {
            try {
                System.out.println("accept...");
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);

                byte[] buffer = new byte[20];
                bis.read(buffer);
                System.out.println(new String(buffer));

                bos.write("Server Echo".getBytes());
                bos.flush();

                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * <ul>
     * <li>ssl连接的重点:</li>
     * <li>初始化SSLServerSocket</li>
     * <li>导入服务端私钥KeyStore，导入服务端受信任的KeyStore(客户端的证书)</li>
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
            ks.load(new FileInputStream("keytool/kserver.keystore"), "aaaaaa".toCharArray());
            tks.load(new FileInputStream("keytool/tserver.keystore"), "aaaaaa".toCharArray());
            kmf.init(ks, "aaaaaa".toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(this.port);
            serverSocket.setNeedClientAuth(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
