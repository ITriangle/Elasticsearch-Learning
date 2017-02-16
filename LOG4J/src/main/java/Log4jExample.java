import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by seentech on 2017/1/19.
 */
public class Log4jExample {

    /* Get actual class name to be printed on */
    static Logger logger = Logger.getLogger(Log4jExample.class.getName());

    public static void main(String[] args) throws IOException {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }

    @Test
    public void test(){
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }

}
