package gprs.com.entity;

import java.io.Serializable;

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
public class Electrical extends Model<Electrical> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("clientid")
	private String clientid;
	@TableField("ip")
	private String ip;
	@TableField("onlineDate")
	private String onlineDate;
	@TableField("downDate")
	private String downDate;
	@TableField("lastOnlineDate")
	private String lastOnlineDate;
    /**
     * 1.在线 2.离线
     */
	@TableField("state")
	private Integer state;
	/**
	 * 设备类型区分（1,wifi、2,gprs）
	 */
	@TableField("type")
	private Integer type;
	
	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getDownDate() {
		return downDate;
	}

	public void setDownDate(String downDate) {
		this.downDate = downDate;
	}

	public String getLastOnlineDate() {
		return lastOnlineDate;
	}

	public void setLastOnlineDate(String lastOnlineDate) {
		this.lastOnlineDate = lastOnlineDate;
	}

	@Override
	public String toString() {
		return "Electrical [id=" + id + ", clientid=" + clientid + ", ip=" + ip + ", onlineDate=" + onlineDate
				+ ", downDate=" + downDate + ", lastOnlineDate=" + lastOnlineDate + ", state=" + state + ", type="
				+ type + "]";
	}

	
}
