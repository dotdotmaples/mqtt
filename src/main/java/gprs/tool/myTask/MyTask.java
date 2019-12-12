package gprs.tool.myTask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import gprs.com.entity.Electrical;
import gprs.com.entity.Msglog;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.mapper.MsglogMapper;
import gprs.tool.dateUitle.DateUtils;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Service
public class MyTask {
	
	@Autowired
	MsglogMapper msglogMapper;
	@Autowired
	ElectricalMapper electricalMapper;
	
	/**
	 * 删除一个月之前的记录
	 */
	 //3.添加定时任务
    @Scheduled(cron = "5 15 1 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
	public void task(){
		String uppermouth = DateUtils.getUpperMonth();
		msglogMapper.deletebydate(uppermouth);
	}
	/**
	 * 踢出不在线设备
	 */
    //3.添加定时任务
    @Scheduled(cron = "5 36 * * * ?")
	public void task1(){
		List<Electrical> electricals = electricalMapper.selectlistbystate();
		for(Electrical electrical :electricals){
			Msglog msglog = msglogMapper.newmsglogbydate(electrical.getClientid()+"/outTopic",DateUtils.getLastHour());
			System.out.println("msg==="+msglog);
			if(msglog==null){
				electricalMapper.updateByMaconline1(electrical.getClientid());
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.getLastHour());
	}
	
}
