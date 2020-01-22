package edu.bit.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.bit.board.page.Criteria;
import edu.bit.board.vo.BoardVO;

public interface BoardMapper { //연속계층, dao에 들어가는 함수들을 여기 넣어야 함.
	
	@Select("select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc")
	public List<BoardVO> selectBoardList();
	
	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle}, #{bContent}, 0, mvc_board_seq.currval, 0, 0 )")
	public void insertBoard(BoardVO param);
	
	@Select("select * from mvc_board where bId = #{bId}")
	public BoardVO selectBoardOne(String bId);
	
	//여기서 전체 게시글 카운팅하는 쿼리문 받아옴
	@Select("select count(*) from mvc_board")
	public int selectCountBoard();
	
	@Update("update mvc_board set bName = #{boardVO.bName}, bTitle = #{boardVO.bTitle}, bContent = #{boardVO.bContent} where bId = #{boardVO.bId}")
	public void updateBoard(@Param("boardVO") BoardVO boardVO);
	
	@Update("update mvc_board set bStep = bStep + 1 where bGroup = #{bGroup} and bStep > #{bStep}")
	public void updateShape(BoardVO boardVO); //객체 하나짜리는 @Param 안 적어줘도 됨.
	
	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle},#{bContent}, #{bGroup}, #{bStep} +1, #{bIndent} +1)")
	public void insertReply(BoardVO boardVO);
	
	@Delete("delete from mvc_board where bId = #{bId}")
	public void deleteBoard(BoardVO boardVO);
	
	public List<BoardVO> selectBoardListPage(Criteria criteria);
	
}
