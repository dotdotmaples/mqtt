package gprs.tool.youmeng;

import org.json.JSONArray;
import org.json.JSONObject;

import gprs.tool.youmeng.android.AndroidBroadcast;
import gprs.tool.youmeng.android.AndroidCustomizedcast;
import gprs.tool.youmeng.android.AndroidGroupcast;
import gprs.tool.youmeng.android.AndroidUnicast;
import gprs.tool.youmeng.ios.Alerts;
import gprs.tool.youmeng.ios.IOSBroadcast;
import gprs.tool.youmeng.ios.IOSCustomizedcast;
import gprs.tool.youmeng.ios.IOSGroupcast;
import gprs.tool.youmeng.ios.IOSUnica;
import gprs.tool.youmeng.ios.IOSUnicast;
import gprs.tool.youmeng.ios.Payloads;

/**
 * 推送实现类
 * 网址：https://www.umeng.com/
 * @author 枫铃也
 *
 */

public class Demo {
	//应用信息中的Appkey
	private String appkey = "5d43a4903fc195bed0000a63";
	private String appkeyIos = "5b9a2528f43e485c4500019b";
	//引用信息中的App Master Secret
	private String appMasterSecret = "88y3ozjnvddou0rvflbdmfaknr8hgups";
	private String appMasterSecretIos = "cari8flx6rigz8bhvd7tepmyd3lic4ff";
/*	private String appkey = "5b7fb438f29d985549000011";
	private String appkeyIos = "5b9a2528f43e485c4500019b";
	private String appMasterSecret = "e4fktrdnjwwc9celxgtzvi1irjkbfviv";
	private String appMasterSecretIos = "cari8flx6rigz8bhvd7tepmyd3lic4ff";
*/	private String timestamp = null;
	private PushClient client = new PushClient();
//	public Demo(String key, String secret) {
//		try {
//			appkey = key;
//			appMasterSecret = secret;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
//	}
	
	public void sendAndroidBroadcast() throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setTicker( "Android broadcast ticker");
		broadcast.setTitle(  "中文的title");
		broadcast.setText(   "Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendAndroidUnicast(String ticker,String title,String text,String deviceToken) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( deviceToken);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(text);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}
	
	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
//	public void sendAndroidCustomizedcastFile() throws Exception {
//		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
//		// TODO Set your alias here, and use comma to split them if there are multiple alias.
//		// And if you have many alias, you can also upload a file containing these alias, then 
//		// use file_id to send customized notification.
//		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
//		customizedcast.setFileId(fileId, "alias_type");
//		customizedcast.setTicker( "Android customizedcast ticker");
//		customizedcast.setTitle(  "中文的title");
//		customizedcast.setText(   "Android customizedcast text");
//		customizedcast.goAppAfterOpen();
//		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
//		// TODO Set 'production_mode' to 'false' if it's a test device. 
//		// For how to register a test device, please see the developer doc.
//		customizedcast.setProductionMode();
//		client.send(customizedcast);
//	}
	
//	public void sendAndroidFilecast() throws Exception {
//		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
//		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
//		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
//		filecast.setFileId( fileId);
//		filecast.setTicker( "Android filecast ticker");
//		filecast.setTitle(  "中文的title");
//		filecast.setText(   "Android filecast text");
//		filecast.goAppAfterOpen();
//		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
//		client.send(filecast);
//	}
	
	public void sendIOSBroadcast(String  xx) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
//		broadcast.setTestMode();
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendIOSUnicast(String deviceToken,String msg) throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkeyIos,appMasterSecretIos);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(msg);
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
//		unicast.setProductionMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge( 0);
		groupcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		groupcast.setTestMode();
		client.send(groupcast);
	}
	
	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
//	public void sendIOSFilecast() throws Exception {
//		IOSFilecast filecast = new IOSFilecast(appkey,appMasterSecret);
//		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
//		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
//		filecast.setFileId( fileId);
//		filecast.setAlert("IOS 文件播测试");
//		filecast.setBadge( 0);
//		filecast.setSound( "default");
//		// TODO set 'production_mode' to 'true' if your app is under production mode
//		filecast.setTestMode();
//		client.send(filecast);
//	}
	
	public static void main(String[] args) {
		// TODO set your appkey and master secret here
//		Demo demo = new Demo("5b7fb438f29d985549000011", "e4fktrdnjwwc9celxgtzvi1irjkbfviv");
		Demo demo = new Demo();
		try {
			demo.sendAndroidUnicast("ceshi","这是标题","这是内容","ApDqGGX_cAmQxcWuwzceTde_mis8tEEfC4tgZdVE6Kjm");
			demo.sendIOSUnicast("47d2fba7c34adebb15e33f1bda32101b31bde4f0f63adad94b80df371303ab44","msg");
			demo.sendIOS();
			demo.sendAndroidBroadcast();
//			demo.sendIOSBroadcast("1104c6bbfa22cf5e2da2bd17849f0bea25e6ab85069a8e0a2810404b9c3e9ce5");
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendIOS() throws Exception{
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		IOSUnica iosUnica = new IOSUnica();
		Payloads payloads = new Payloads();
		Alerts alerts = new Alerts();
		alerts.setBody("{'after_open':'go_app'}");
		alerts.setSubtitle("buxiaode");
		alerts.setTitle("biaoti");
		payloads.setDisplay_type("notification");
		payloads.setAps(alerts);
		payloads.setBody("{'after_open':'go_app','title':'ceshi','text':'zheshiceshi'}");
		iosUnica.setAppkey(appkeyIos);
		iosUnica.setDevice_tokens("47d2fba7c34adebb15e33f1bda32101b31bde4f0f63adad94b80df371303ab44");
		iosUnica.setPayload(payloads);
		iosUnica.setProduction_mode("false");
		iosUnica.setTimestamp(timestamp);
		iosUnica.setType("unicast");
		iosUnica.setAppMasterSecret(appMasterSecretIos);
		client.send1(iosUnica);
	} 

}
