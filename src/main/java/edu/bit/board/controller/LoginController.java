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
	
	//�α���
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest req, RedirectAttributes rttr) { //RedirectAttributes ���� ������ �ִ� ��. ���ΰ�ħ�ϸ� ���ư��� ������(��ȸ��)
		System.out.println("login ȣ��");
		
		//HttpServletRequest �ȿ� HttpSession�� ����. ������ Ŭ���̾�Ʈ�� ���� ���� ��Ĺ�� ���ؼ� id�� ��.
		//servers���� web.xml���� session ���� �ð� ������ �� ����. -> ��� ���ø����̼ǿ� ����
		//�츮 web.xml�� session-config �ϰ� session-timeout�ϸ� session ���� �ð� ������ �� ����. -> ������ ���ø����̼��� ����
		HttpSession session = req.getSession();
		
		//id�� pw�� ����
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		//DBȮ���ؾ� ��
		UserVO login = loginService.loginUser(id, pw);
		
		//login üũ, �α����� ��Ų�� -> sessionó��
		if(login == null) {
			rttr.addFlashAttribute("msg", false); //msg�� ������Ÿ���� boolean��.
		} else {
			session.setAttribute("member", login);
		}
		
		return "redirect:/";
	}
	
	//�α׾ƿ�
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) { //session�� HttpSession �� �ȿ� ����(Spring���� ��������)
		System.out.println("logout ȣ��");
		
		session.invalidate(); //session ����
		
		return "redirect:/";
	}
	
	
	
	
}
