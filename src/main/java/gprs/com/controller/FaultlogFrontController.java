package gprs.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.ExceptionController;
import gprs.com.service.FaultlogServiceInter;

/**
 * <p>
 *  前端控制器--故障
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Controller
public class FaultlogFrontController extends ExceptionController{
	
	@Autowired
	FaultlogServiceInter faultlogServiceInter;
	
	/**
	 * 故障信息列表
	 * @param pageNo
	 * @param pageSize
	 * @param equipmentNumber
	 * @return
	 */
	@RequestMapping("front/faultlog/faulyloglist")
	@ResponseBody
	public Object faulyloglist(Integer pageNo,Integer pageSize,String equipmentNumber){
		return faultlogServiceInter.faulyloglist(pageNo,pageSize,equipmentNumber);
	}
	
	/**
	 * 获取用户故障数量
	 * @param token
	 * @return
	 */
	@RequestMapping("front/faultlog/getFaultlogNumber")
	@ResponseBody
	public Object getFaultlogNumber(String token){
		return faultlogServiceInter.getFaultlogNumber(token);
	}
	
	/**
	 * 获取故障列表
	 * @param pageNo
	 * @param pageSize
	 * @param equipmentNumber
	 * @return
	 */
	@RequestMapping("back/faultlog/getfaulyloglist")
	@ResponseBody
	public Object getfaulyloglist(Integer pageNo,Integer pageSize,String equipmentNumber){
		return faultlogServiceInter.faulyloglist(pageNo,pageSize,equipmentNumber);
	}
	
	/**
	 * 删除指定mac的故障
	 * @param mac
	 * @return
	 */
	@RequestMapping("front/faultlog/deletfaulylogbyMac")
	@ResponseBody
	public Object deletfaulylog(String mac){
		return faultlogServiceInter.deletfaulylogbyMac(mac);
	}
	
}
