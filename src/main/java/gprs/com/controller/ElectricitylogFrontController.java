package gprs.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.ExceptionController;
import gprs.com.service.ElectricitylogServiceInter;

/**
 * <p>
 *  前端控制器--设备用电量
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Controller
@RequestMapping("front/electricitylog")
public class ElectricitylogFrontController extends ExceptionController{
	
	@Autowired
	ElectricitylogServiceInter electricitylogServiceInter;
	
	@RequestMapping("electricityloglist")
	@ResponseBody
	public Object electricityloglist(Integer pageNo,Integer pageSize,String equipmentNumber,String searchTime){
		
		return electricitylogServiceInter.electricityloglist(pageNo,pageSize,equipmentNumber,searchTime);
	}
	
}
