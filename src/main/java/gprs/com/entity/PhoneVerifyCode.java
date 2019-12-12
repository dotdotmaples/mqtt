package gprs.com.entity;

import java.io.Serializable;

/**
 *<p>类  说  明: TODO 手机验证码
 *<p>创建时间: 2017年11月4日 下午3:10:17
 *<p>创  建  人: geYang
 **/
public class PhoneVerifyCode  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1106132718394043761L;
	private String phone;
	private String verifyCode;
	private long startTime;
	private long endTime;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "PhoneVerifyCode [phone=" + phone + ", verifyCode=" + verifyCode + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

	
	
}
