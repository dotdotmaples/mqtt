package gprs.com.entity;

/**
* @ClassName: GprsOnlineInfo
* @Description: 设备等待匹配表。 如果设备未被匹配，则存入此表
* @author guobin
* @date 2019年7月1日
*/
public class GprsOnlineInfo {
    
    /**
     * 设备编号
     */
    private String imei;
    
    /**
     * 等待匹配时间
     */
    private String startTime;
    
    /**
     * 匹配状态，0未匹配，1已匹配
     */
    private Integer state;


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    
    
    
}
