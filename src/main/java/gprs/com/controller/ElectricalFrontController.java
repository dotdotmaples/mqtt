package gprs.com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.service.ElectricalServiceInter;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
@Controller
public class ElectricalFrontController {

	@Resource
	ElectricalServiceInter electricalServiceInter;
	
	/**
	 * 后端设备列表
	 * @param clientid 模糊搜索
	 * @param state 1 在线 2 离线
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("back/electrical/electricalList")
	@ResponseBody
	public Object electricalList(String clientid,Integer state,Integer pageSize,Integer pageNo){
		return electricalServiceInter.electricalList(clientid,state,pageSize,pageNo);
	}
	

}
