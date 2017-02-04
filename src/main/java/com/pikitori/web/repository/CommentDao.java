package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.CommentVo;

@Repository
public class CommentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CommentVo> showReplyList(CommentVo comment){
		return sqlSession.selectList("comment.showreplyList",comment);
		
	}
	public void addReply(CommentVo postReply){
		sqlSession.insert("comment.addReply", postReply);
	}
}
