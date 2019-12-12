package gprs.com.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.ExceptionController;
import gprs.com.service.MsglogServiceInter;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Controller
public class MsglogFrontController extends ExceptionController{
	
	@Resource
	MsglogServiceInter MsglogServiceInter;
	
	/**
	 * 最近消息
	 * @param topic
	 * @return
	 */
	@RequestMapping("front/msglog/newMsglog")
	@ResponseBody
	public Object newmsglog(String topic){
		return MsglogServiceInter.newmsglog(topic);
	}
	
	/**
	 * 获取电费
	 * @param mac
	 * @return
	 */
	@RequestMapping("front/msglog/getele")
	@ResponseBody
	public Object getele(String mac){
		return MsglogServiceInter.getele(mac);
		
	}
	
	
	/**
	 * 消息转发
	 * 修改电费
	 */
	@RequestMapping("front/msglog/forwardMsg")
	@ResponseBody
	public Object forwardMsg(String mac,Double eleset){
		System.out.println("开始=="+new Date().getTime());
		return MsglogServiceInter.forwardMsg(mac,eleset);
	}
	
	/**
	 * 设备参数设置
	 * @param mac
	 * @param LK_SET 额定电压
	 * @param RC_SET 额定电流
	 * @param UV_SET 欠压
	 * @param OV_SET 过压
	 * @param Trip_bit 偏移量
	 * @param deviceName 设备名称
	 * @return
	 */
	@RequestMapping("front/msglog/electricalSetup")
	@ResponseBody
	public Object electricalSetup(String mac,Integer LK_SET,Integer RC_SET,Integer UV_SET,Integer OV_SET,String Trip_bit,String deviceName){
		return MsglogServiceInter.forwardMsg(mac,LK_SET,RC_SET,UV_SET,OV_SET,Trip_bit,deviceName);
	}
	
	/**
	 * 发送指令
	 */
	@RequestMapping("front/msglog/instructions")
	@ResponseBody
	public Object instructions(String mac,Integer order){
		return MsglogServiceInter.instructions(mac,order);
	}
	
	
	/**
	 * 消息记录
	 */
	@RequestMapping("back/msglog/msgloglist")
	@ResponseBody
	public Object msgloglist(String mac,Integer pageSize,Integer pageNo,String searchdate){
		return MsglogServiceInter.msgloglist(mac,pageSize,pageNo,searchdate);
	}
	
	/**
	 * 删除记录
	 */
	@RequestMapping("back/msglog/deletemsglog")
	@ResponseBody
	public Object deletemsglog(String id){
		return MsglogServiceInter.deletemsglog(id);
	}
	
}
