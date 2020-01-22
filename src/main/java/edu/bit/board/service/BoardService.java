package edu.bit.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bit.board.mapper.BoardMapper;
import edu.bit.board.page.Criteria;
import edu.bit.board.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	BoardMapper boardMapper; //연속계층 가져옴
	
	//게시판 List를 위한 출력하는 로직
	//List의 기능은 안에 비지니스 로직이 하나밖에 없음
	public List<BoardVO> selectBoardList() {
		return boardMapper.selectBoardList();
	}
	
	//게시판에서 리플을 쓰는 로직
	//쿼리문 2개임.. 그래서 함수 두개 가져와야 함
	public void writeReply(BoardVO boardVO) {
		boardMapper.updateShape(boardVO); //step과 indent
		boardMapper.insertReply(boardVO); //글 insert시키는 것
	}

	public void insertBoard(BoardVO boardVO) {
		boardMapper.insertBoard(boardVO);
		
	}

	public BoardVO selectBoardOne(String bId) {
		return boardMapper.selectBoardOne(bId);
	}

	public void updateBoard(BoardVO boardVO) {
		boardMapper.updateBoard(boardVO);
		
	}

	public void deleteBoard(BoardVO boardVO) {
		boardMapper.deleteBoard(boardVO);
		
	}

	public int selectCountBoard() {
		return boardMapper.selectCountBoard();
	}
	
	public List<BoardVO> selectBoardListPage(Criteria criteria) {
		return boardMapper.selectBoardListPage(criteria);
	}
}
