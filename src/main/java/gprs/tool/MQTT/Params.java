package gprs.tool.MQTT;

public class Params {

    /**
     * gprs统一订阅主题  "/AFDD1"
     */
    public static String GPRS_TOPIC = "/AFDD1";
 
    
    /**
     * 设备等待匹配时间：180000ms/3m
     */
    public static Integer WAIT_TIME = 180000;
    
    /**
     * WIFI模板的连接名coulet
     */
    public static String WIFI_NAME = "coulet";
    
    /**
     *   GPRS模板的连接名  "gprs"
     */
    public static String GPRS_NAME = "gprs";
    
    /**
     *   wifi模块类型  type = 1
     */
    public static Integer EQUIPMENT_WIFI = 1;
    
    /**
     *   gprs模块类型 type = 2
     */
    public static Integer EQUIPMENT_GPRS = 2;
    
    /**
     *  设备等待匹配 state = 0
     */
    public static Integer GPRS_CONNECT_WAIT = 0;
    
    /**
     *  设备匹配成功 state = 1
     */
    public static Integer GPRS_CONNECT_SUCCESS = 1;
    
    public static void main(String[] args) {
        String a = "inTopic/860344046420304";
        System.out.println(a.substring(a.indexOf("/")+1));
        
    }
    
    
}
