package edu.bit.board.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.bit.board.vo.UserVO;

//preHandle과 postHandle함수
//Interceptor는 해킹과 같다... 왜냐하면 DispatcherServlet과 Controller와의 주고받는걸 관여해서
//request와 response를 Interceptor로 조작할 수 있어서..
public class BoardInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
		
		System.out.println("preHandle 실행");
		
		//session 객체를 가져옴
		HttpSession session = request.getSession();
		
		//login 처리를 담당하는 사용자 정보를 담고 있는 객체를 가져옴
		UserVO member = (UserVO) session.getAttribute("member");
		
		if (member == null) {
			//로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
			response.sendRedirect(request.getContextPath());
			return false; //더 이상 컨트롤러 요청으로 가지 않돌고 false로 반환함.
		}
		
		return true; //preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임. 따라서 true로 하면 컨트롤러가 uri로 가게 됨.
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
		System.out.println("postHandle 실행");
	}
}
