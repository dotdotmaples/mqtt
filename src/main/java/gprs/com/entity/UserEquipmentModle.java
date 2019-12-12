package gprs.com.entity;

/**
 * 用户设备中间表
 * @author yvdedu.com
 *
 */
public class UserEquipmentModle {

	private Integer userId;
	private Integer equipId;
	private String name;
	private String umengToken;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getEquipId() {
		return equipId;
	}
	public void setEquipId(Integer equipId) {
		this.equipId = equipId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUmengToken() {
		return umengToken;
	}
	public void setUmengToken(String umengToken) {
		this.umengToken = umengToken;
	}
	@Override
	public String toString() {
		return "UserEquipmentModle [userId=" + userId + ", equipId=" + equipId + "]";
	}
	
	
}
