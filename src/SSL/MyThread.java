package SSL;

/**
 * Created by TOM on 2017/3/30.
 */
public class MyThread implements Runnable{

    public void run(){
        TestSSLSocketClient.run();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new MyThread()).start();
        }
    }
}
