package gprs.com.service;

import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Msglog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface MsglogServiceInter extends IService<Msglog> {

	Object newmsglog(String topic);
	
	//消息转发
	Object forwardMsg(String topic,Double eleset);

	//参数设置
	Object forwardMsg(String mac, Integer lK_SET, Integer rC_SET, Integer uV_SET, Integer oV_SET, String trip_bit,String deviceName);

	//发送指令
	Object instructions(String mac, Integer order);
	
	//消息记录
	Object msgloglist(String mac, Integer pageSize, Integer pageNo,String searchdate);

	Object deletemsglog(String id);

	Object getele(String mac);
	
}
