package gprs.com.interceptor;

import java.util.concurrent.ExecutorService;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import gprs.tool.MQTT.CustomThreadPoolExecutor;
import gprs.tool.MQTT.MqttSession;

@Component
public class ServletContextListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();  
        MqttSession.map.clear();
        pool.shutdown();
    }


}
