package gprs.com.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import gprs.com.entity.Electrical;
import gprs.com.entity.MqttClient;
import gprs.com.entity.Msglog;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.mapper.MsglogMapper;
import gprs.tool.dateUitle.DateUtils;

@Controller
public class OtherController {

    
    @Autowired
    MsglogMapper msglogMapper;
    @Autowired
    ElectricalMapper electricalMapper;
    
    private static String HOST = "118.24.248.24:18083";
    
    /**
     * 踢出不在线设备
     */
    public void task1(){
        List<Electrical> electricals = electricalMapper.selectlistbystate();
        for(Electrical electrical :electricals){
            Msglog msglog = msglogMapper.newmsglogbydate(electrical.getClientid()+"/outTopic",DateUtils.getYesterday());
            System.out.println("msg==="+msglog);
            if(msglog==null){
                electricalMapper.updateByMaconline1(electrical.getClientid());
            }
        }
    }
    
    // Http Basic基本认证
    @RequestMapping("online")
    @ResponseBody
    public Object online() {
        String URI = "http://" + HOST + "/api/v2/nodes/emq@127.0.0.1/clients";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置BasicAuth
        CredentialsProvider provider = new BasicCredentialsProvider();
        // Create the authentication scope
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        // Create credential pair，在此处填写用户名和密码
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "public");
        // Inject the credentials
        provider.setCredentials(scope, credentials);
        // Set the default credentials provider
        httpClientBuilder.setDefaultCredentialsProvider(provider);
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        
        String result = "";
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet(URI);
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            System.out.println(httpResponse);
            entity = httpResponse.getEntity();
            if( entity != null ){
                result = EntityUtils.toString(entity);
            }
            // 关闭连接
            closeableHttpClient.close();
        } catch (ClientProtocolException e) {
            return "error" + e.getMessage();
        } catch (IOException e) {
            return "error" + e.getMessage();
        }
        /*// 关闭连接
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            return "error" + e.getMessage();
        }*/
        System.out.println(result);
        JSONObject parseObject = JSON.parseObject(result);
        if (parseObject.getInteger("code") == 0) {
            JSONObject jsonObject = parseObject.getJSONObject("result");
            if (jsonObject.getInteger("total_num") > 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("objects");
                List<MqttClient> parseArray = JSONArray.parseArray(jsonArray.toJSONString(), MqttClient.class);
//                MqttClient[] a = null;
//                List<MqttClient> asList = Arrays.asList(jsonArray.toArray(a));
                // 下线所有，然后上线
                electricalMapper.unOnline();
                String clientIds = "";
                int num = 0;
                Pattern pattern = Pattern.compile("[0-9]*");
                for (MqttClient mqttClient : parseArray) {
                    Matcher isNum = pattern.matcher(mqttClient.getClient_id());
                    if( isNum.matches() ){
                        num += 1;
                        clientIds += mqttClient.getClient_id() + ",";
                    }
                }
                System.out.println(clientIds);
                // 上线
                electricalMapper.online(clientIds);
                return "server online[" + num + "] : " + clientIds;
            }
        }
        return "error";
    }
    
    
}
