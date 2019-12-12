package gprs.com.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
public class Equipmentlist extends Model<Equipmentlist> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户设备关联表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String mac;
	private String initmac;
    /**
     * 关联时间
     */
	private Date lastTime;
	
	private String wifimac;
	private Integer type;
	
    /**
     * 用户id
     */
	private Integer userId;
	private String devicename;
	private Double eleset;
	
	/**
	 * 是否在线
	 */
	@TableField(exist=false)
	private Integer state;
	
	@TableField(exist=false)
	private Integer faultynumber;
	
	/**
	 * equipmentType 设备类型区分（1,wifi、2,gprs）
	 */
	@TableField(exist=false)
	private Integer equipmentType;

	

	public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Integer getFaultynumber() {
		return faultynumber;
	}

	public void setFaultynumber(Integer faultynumber) {
		this.faultynumber = faultynumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWifimac() {
		return wifimac;
	}

	public void setWifimac(String wifimac) {
		this.wifimac = wifimac;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Double getEleset() {
		return eleset;
	}

	public void setEleset(Double eleset) {
		this.eleset = eleset;
	}

	public String getInitmac() {
		return initmac;
	}

	public void setInitmac(String initmac) {
		this.initmac = initmac;
	}

	@Override
	public String toString() {
		return "Equipmentlist [id=" + id + ", mac=" + mac + ", initmac=" + initmac + ", lastTime=" + lastTime
				+ ", wifimac=" + wifimac + ", type=" + type + ", userId=" + userId + ", devicename=" + devicename
				+ ", eleset=" + eleset + ", state=" + state + ", faultynumber=" + faultynumber + ", equipmentType="
				+ equipmentType + "]";
	}
	
	

}
