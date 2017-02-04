package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.PostService;
import com.pikitori.web.vo.PictureVo;
import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/mecavo/post")
public class PostController {
		 
	@Autowired
	private PostService postService;
		
	@RequestMapping("/add" )
	@ResponseBody
	public Object addPost(@RequestBody PostVo postVo, HttpSession session){
		System.out.println(postVo.toString());
		
		postService.addPost(postVo);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "success");
		map.put("data", postVo);
		System.out.println("-------------------Controller add"+postVo);
		
		return map;
	}
	
	@RequestMapping("/showpostList")
	@ResponseBody
	public Object fetchlistPost(@RequestBody UserVo user, HttpSession session){
		System.out.println("showpostList: " + user);
		List<PostVo> list = postService.showPostList(user);
		System.out.println("-------------------Controller show "+list);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "success");
		map.put("data", list);
		
		return map;
	}
	
	
}
