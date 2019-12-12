package gprs.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gprs.com.entity.Admin;
import gprs.com.mapper.AdminMapper;
import gprs.tool.resulte.MyResult;
import net.sf.json.JSONObject;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter{

	
//	private static Logger logger = Logger.getLogger(UserInterceptor.class);

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
			{
//		logger.debug("后台拦截器StudentInterceptor");
		String requestUri = request.getServletPath();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "*");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept"); 
        //
 		String token = request.getParameter("token");
		
 		if(token==null || "".equals(token)){
 			System.out.println("后台拦截器");
 			response.setContentType("application/json; charset=utf-8");
 	 		MyResult error = MyResult.loginError("登录状态丢失，请重新登录！");
 	 		JSONObject object = JSONObject.fromObject(error);
 	 		response.getWriter().print(object);
 			return false;
 		}else {
 			Admin userId = adminMapper.getAdminbyToken(token);
 			if (userId != null){
 				request.setAttribute("token", token);
 				return true;
 			}else {
 				System.out.println("后台拦截器：登录状态丢失，请重新登录！");
 				response.setContentType("application/json; charset=utf-8");
 		 		MyResult error = MyResult.loginError("登录状态丢失，请重新登录！");
 		 		JSONObject object = JSONObject.fromObject(error);
 		 		response.getWriter().print(object);
 		 		return false;// 终止后面的拦截器的执行
			}  
	 	}
	}
}
