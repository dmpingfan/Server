package Thread;

/**
 * Created by dangmei on 2017/3/8.
 */
class RunnableDemo extends Thread {
    private String threadName;

    RunnableDemo( String name){
        threadName = name;
        System.out.println("Creating " +  threadName );
    }
    public static void main(String args[]) {
        for (int i = 0; i < 5; i++) {
            Thread R1 = new RunnableDemo( "Thread-"+i);
            R1.start();
        }
    }
    @Override
    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }
@Override
    public void start ()
    {
        System.out.println("Thread " +  threadName + " starting.");
    }

}