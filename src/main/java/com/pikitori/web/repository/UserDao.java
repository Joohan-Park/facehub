package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<UserVo> getList(){
		return sqlSession.selectList("user.getAlluser");
	}

	public UserVo getEmail(String user_id){
		return sqlSession.selectOne("user.checkEmail", user_id);
	}
	
	public UserVo getNickname(String user_name){
		return sqlSession.selectOne("user.checkNickname",user_name);
	}
	
	public boolean addUser(UserVo uservo){
		int count = sqlSession.insert("user.addUser",uservo);
		return ( count > 0)? true : false;
	}
	
	public UserVo Login(UserVo user){
		return sqlSession.selectOne("user.getByEmailAndPassword",user);
	}
	
	public UserVo isExistF_User(UserVo uservo){
		return sqlSession.selectOne("user.getBySocialID",uservo);
	}
	
	public boolean addF_User(UserVo uservo){
		if( "".equals(uservo.getUser_id())){
			uservo.setUser_id(null);
		}
		int count = sqlSession. insert("user.addF_User", uservo);
		return ( count > 0)? true : false;
	}
}
