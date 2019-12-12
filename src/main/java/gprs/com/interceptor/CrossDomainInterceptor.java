package gprs.com.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
*~~(☆＿☆)~~
* @ClassName: CrossDomainInterceptor 
* @Description: TODO(拦截请求解决跨域问题) 
* @author zlw
* @date 2018年4月16日 上午8:56:08 
*
 */
@Component
public class CrossDomainInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws IOException{
		if(!request.getServletPath().equals("/richtext/upload")){
			response.setHeader("Access-Control-Allow-Origin", "*");
		}else { 
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		}
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials","true");
        
		return true;
	}

}
