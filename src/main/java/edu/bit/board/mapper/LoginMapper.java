package edu.bit.board.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import edu.bit.board.vo.UserVO;


public interface LoginMapper { 
	
	@Select("select * from users where username = #{username} and password = #{password}")
	public UserVO loginUser(@Param("username")String username, @Param("password")String password);
	
}
