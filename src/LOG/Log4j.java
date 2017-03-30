package LOG;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by dangmei on 2017/3/8.
 */
public class Log4j {
    private static final Logger logger = Logger.getLogger(Log4j.class.getName());
    static {
        PropertyConfigurator.configure("conf/log4j.properties");
    }
    public static void main(String[] args){
        System.out.println("begin");
        logger.debug("读取配置：locahost");
        logger.info("写入成功");
        logger.error("写入错误");
        logger.fatal("系统文件丢失");
    }

}
