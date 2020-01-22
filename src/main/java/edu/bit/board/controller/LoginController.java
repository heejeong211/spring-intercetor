package edu.bit.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.bit.board.service.LoginService;
import edu.bit.board.vo.UserVO;


@Controller
@RequestMapping("/member")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	//로그인
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest req, RedirectAttributes rttr) { //RedirectAttributes 단일 데이터 넣는 것. 새로고침하면 날아가는 데이터(일회성)
		System.out.println("login 호출");
		
		//HttpServletRequest 안에 HttpSession이 있음. 실제로 클라이언트에 가는 것은 톰캣을 통해서 id가 감.
		//servers에서 web.xml에서 session 지속 시간 설정할 수 있음. -> 모든 어플리케이션에 적용
		//우리 web.xml에 session-config 하고 session-timeout하면 session 지속 시간 설정할 수 있음. -> 각각의 어플리케이션의 적용
		HttpSession session = req.getSession();
		
		//id와 pw를 받음
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		//DB확인해야 함
		UserVO login = loginService.loginUser(id, pw);
		
		//login 체크, 로그인을 시킨다 -> session처리
		if(login == null) {
			rttr.addFlashAttribute("msg", false); //msg의 데이터타입은 boolean임.
		} else {
			session.setAttribute("member", login);
		}
		
		return "redirect:/";
	}
	
	//로그아웃
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) { //session은 HttpSession 이 안에 있음(Spring에서 지원해줌)
		System.out.println("logout 호출");
		
		session.invalidate(); //session 삭제
		
		return "redirect:/";
	}
	
	
	
	
}
