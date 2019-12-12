package gprs.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gprs.com.mapper.TokenMapper;
import gprs.tool.resulte.MyResult;
import net.sf.json.JSONObject;

/**
 * 
 * ~~(☆＿☆)~~
 * 
 * @ClassName: UserInterceptor
 * @Description: TODO(拦截前台用户是否登录)
 * @date 2018年6月6日 上午8:54:57
 *
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	TokenMapper tokenMapper;

//	private static Logger logger = Logger.getLogger(UserInterceptor.class);

	/*
	 * @Autowired private UserMapper um;
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		logger.debug("前台拦截器StudentInterceptor");
//		String requestUri = request.getServletPath();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		String token = request.getParameter("token");

		if (token == null) {
			System.out.println("前台拦截器");
			response.setContentType("application/json; charset=utf-8");
			MyResult error = MyResult.loginError("登录状态丢失，请重新登录！");
			JSONObject object = JSONObject.fromObject(error);
			response.getWriter().print(object);
			return false;
		} else {
			Integer userId = tokenMapper.getUserIdbyToken(token);
			if (userId != null) {
				request.setAttribute("token", token);
				return true;
			} else {
				System.out.println("前台拦截器");
				response.setContentType("application/json; charset=utf-8");
				MyResult error = MyResult.loginError("登录状态丢失，请重新登录！");
				JSONObject object = JSONObject.fromObject(error);
				response.getWriter().print(object);
				return false;// 终止后面的拦截器的执行
			}
		}
	}

}
