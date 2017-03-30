package LOG;


import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dangmei on 2017/3/8.
 */
public class Logging {
    static Logger logger = Logger.getLogger(Logging.class.getName());

    public static void main(String[] args)throws IOException{
        Logger log = Logger.getLogger("lavasoft");
        System.out.println("begin");
        ConsoleHandler consoleHandler =new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);
        FileHandler fileHandler = new FileHandler("logs/logging%u.log");
        fileHandler.setLevel(Level.INFO);
        log.addHandler(fileHandler);
        log.info("aaa");

        logger.finer("比较美观");
        logger.fine("界面优化");
        logger.config("读取配置：locahost");
        logger.info("写入成功");
        logger.warning("写入错误");
        logger.severe("系统文件丢失");
    }
}
