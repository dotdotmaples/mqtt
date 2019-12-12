package gprs;


import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.fastjson.JSON;

@SpringBootApplication
@EnableTransactionManagement
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING) 
@MapperScan({"gprs.com.mapper"})
public class MqttGprsApplication extends SpringBootServletInitializer{

    /**
    * tomcat运行
    * 启动类 继承SpringBootServletInitializer并重写configure方法
    * @param builder
    * @return
    */
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       return builder.sources(MqttGprsApplication.class);
   }
   
    /**
     * 简单的启动  加载默认配置文件名称   》》》  application.properties/yml
     */
    public static void main(String[] args) {
        SpringApplication.run(MqttGprsApplication.class, args);
    }
    
     /**
     * 加载指定的配置文件 》》》 xxx.properties/yml
     */
//  public static void main (String[] args) throws IOException {
//      Properties properties = new Properties();
//      InputStream resourceAsStream = Application.class.getClassLoader().getResourceAsStream("application.properties");
//      properties.load(resourceAsStream);
//      SpringApplication springApplication = new SpringApplication(WebApplication.class);
//      springApplication.setDefaultProperties(properties);
//      springApplication.run(args);
//  }

    
}
