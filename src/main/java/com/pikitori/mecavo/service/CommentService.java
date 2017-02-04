package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.CommentDao;
import com.pikitori.web.vo.CommentVo;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;
	
	public List<CommentVo> showReplyList(CommentVo comment){
		List<CommentVo> list=commentDao.showReplyList(comment);
		return list;
	}
	
	public void addReply(CommentVo postReply){
		commentDao.addReply(postReply);
		
	}
}
