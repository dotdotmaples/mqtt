package gprs.com.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.tool.resulte.MyResult;



/**
 * 
*~~(☆＿☆)~~
* @ClassName: ExceptionController 
* @Description: TODO(异常集中处理类，通过Controller继承该类实现) 
* @author zlw
* @date 2018年4月17日 下午3:54:35 
*
 */
public class ExceptionController { 
	@ResponseBody
    @ExceptionHandler  
    public Object exp(HttpServletRequest request, MyException e) {
		e.printStackTrace();
    	LogFactory.getLog(super.getClass()).error(e.getMessage(), e);
		return MyResult.getFalse(e.getMessage()); 
    } 
}
