package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.CommentService;
import com.pikitori.web.vo.CommentVo;

@Controller
@RequestMapping("/mecavo/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@RequestMapping("/showreplyList")
	@ResponseBody
	public Object showReplyList(
			@ModelAttribute CommentVo comment) {
		List<CommentVo> list = commentService.showReplyList(comment);
		System.out.println(comment.getPost_no());
		System.out.println(comment.getUser_no());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);
		System.out.println(map.get("data"));
		return map;

	}

	@RequestMapping("/addreply")
	@ResponseBody
	public void addReply(@ModelAttribute CommentVo postReply) {
		System.out.println(postReply.getComment_content());
		commentService.addReply(postReply);

	}
}
