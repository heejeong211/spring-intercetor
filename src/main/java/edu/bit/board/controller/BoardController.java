package edu.bit.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.board.page.Criteria;
import edu.bit.board.page.PageMaker;
import edu.bit.board.service.BoardService;
import edu.bit.board.vo.BoardVO;
import edu.bit.board.vo.UserVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Inject
	BoardService boardService;
	
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		System.out.println("list()");
		
		/*
		 * //로그인된 사용자만 list보는 방법 UserVO member = (UserVO)
		 * session.getAttribute("member");
		 * 
		 * //첫번째 방법 - 단점: 이걸 하나씩 다 넣어줘야 함. 그래서 aop를 사용하거나 interceptor를 사용해야 함. if(member
		 * == null) { System.out.println("멤버세션값 없음"); return "redirect:/"; }
		 */
		
		model.addAttribute("list", boardService.selectBoardList());
		
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(BoardVO boardVO, Model model) {
		System.out.println("write()");
		
		boardService.insertBoard(boardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		String bId = request.getParameter("bId");
		model.addAttribute("content_view", boardService.selectBoardOne(bId));
		
		return "content_view";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVO boardVO, Model model) {
		System.out.println("modify()");
		
		boardService.updateBoard(boardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");
		
		String bId = request.getParameter("bId");
		model.addAttribute("reply_view", boardService.selectBoardOne(bId));
		
		return "reply_view";
	}
	
	@RequestMapping("/reply")
	public String reply(BoardVO boardVO, Model model) {
		System.out.println("reply()");
		
		boardService.writeReply(boardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(BoardVO boardVO, Model model) { //BoardVO에 컬럼이름이랑 변수명이 일치될 때 boardVO에 저장됨
		System.out.println("delete()");
		
		boardService.deleteBoard(boardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/list2")
	public String list2(Criteria criteria, Model model) { //Model은 주소 넣어서 IoC컨테이너가 주입시켜줌.
		System.out.println("list2()");
		
		PageMaker pageMaker = new PageMaker(); //bean을 이용해서 객체 생성 안함
		pageMaker.setCri(criteria);
		
		System.out.println(criteria.getPerPageNum());
		System.out.println(criteria.getPage());
		
		//전체 게시글 카운팅해주는 함수 불러옴
		int totalCount = boardService.selectCountBoard();
		System.out.println("전체 게시물 수를 구함: " + totalCount);
		
		//전체값 세팅
		pageMaker.setTotalCount(totalCount);
		
		List<BoardVO> boardList = boardService.selectBoardListPage(criteria); //Criteria는 어떻게 넘어올까? PageMaker에서 Criteria가 세팅되서 넘어옴. IoC 컨테이너가 디폴트 생성자를 보고 알아서 객체 생성해서 가지고 옴. 그래서 처음값은 1하고 10이다.
		
		model.addAttribute("list", boardList);
		model.addAttribute("pageMaker", pageMaker);
		
		return "list2";
	}
	
}
