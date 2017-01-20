import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by seentech on 2017/1/20.
 */
public class PropertiesTest {

    @Test
    public void propertiesTest() throws IOException {
        String ConfPath =  "E:\\WLGIT\\Elasticsearch-master\\LOG4J\\src\\main\\resources\\application.properties";

        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(ConfPath);

        Properties properties = new Properties();
        //加载文件输入流
        properties.load(fileInputStream);

        //获取配置文件中key列表
        Enumeration enumeration = properties.propertyNames();

        //通过key获取相应的value
        while (enumeration.hasMoreElements()){
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);

            System.out.println(key + "=" + value);
        }
    }


}
