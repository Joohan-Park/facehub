package com.pikitori.web.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.FollowVo;
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
	
	public UserVo LoginbyIndex(UserVo user) {
		System.out.println("LoginbyIndex");
		return sqlSession.selectOne("user.getBySocailIndex",user);
	}
	
	public UserVo isExistF_User(UserVo uservo){
		return sqlSession.selectOne("user.getBySocialID",uservo);
	}
	
	public boolean addF_User(UserVo uservo){
		/* ""값은 uniq 에러 발생.*/
		if( "".equals(uservo.getUser_id())){
			uservo.setUser_id(null);
		}
		int count = sqlSession. insert("user.addF_User", uservo);
		return ( count > 0)? true : false;
	}
	public UserVo updateProfile(UserVo uservo) {
		int result = sqlSession.update("user.update_profile",uservo);
		return  (result > 0)? sqlSession.selectOne("user.getByNo",uservo.getUser_no()) : null;
	}
	
	public int updateProfileImg(String filepath, UserVo user) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("url",filepath);
		map.put("user_id", user.getUser_id());
		System.out.println(user.getUser_id());
		return sqlSession.update("user.update_profileUrl", map);
	}
	
	public UserVo getUser(Long user_no){
		return sqlSession.selectOne("user.getByNo",user_no);
	}
	
	public List<UserVo> searchNameList(String str){
		return sqlSession.selectList("user.searchNameList", str);
	}
	
	public List<FollowVo> followlist(UserVo user){
		return sqlSession.selectList("user.followlist", user);
	}
	
	public List<FollowVo> followerlist(UserVo user){
		return sqlSession.selectList("user.followerlist", user);
	}

}
