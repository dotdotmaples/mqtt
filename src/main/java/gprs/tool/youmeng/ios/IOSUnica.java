package gprs.tool.youmeng.ios;

import org.json.JSONObject;

public class IOSUnica {

	protected final JSONObject rootJson = new JSONObject();
	
	private String appkey;
	private String timestamp;
	private String type;
	private String production_mode;
	private String device_tokens;
	private Payloads payload;
	private String appMasterSecret;
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProduction_mode() {
		return production_mode;
	}
	public void setProduction_mode(String production_mode) {
		this.production_mode = production_mode;
	}
	public String getDevice_tokens() {
		return device_tokens;
	}
	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}
	public Payloads getPayload() {
		return payload;
	}
	public void setPayload(Payloads payload) {
		this.payload = payload;
	}
	
	
	public String getAppMasterSecret() {
		return appMasterSecret;
	}
	public void setAppMasterSecret(String appMasterSecret) {
		this.appMasterSecret = appMasterSecret;
	}
	@Override
	public String toString() {
		return "IOSUnica [rootJson=" + rootJson + ", appkey=" + appkey + ", timestamp=" + timestamp + ", type=" + type
				+ ", production_mode=" + production_mode + ", device_tokens=" + device_tokens + ", payload=" + payload
				+ ", appMasterSecret=" + appMasterSecret + "]";
	}
	
}
