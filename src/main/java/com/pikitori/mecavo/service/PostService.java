package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.PictureDao;
import com.pikitori.web.repository.PostDao;
import com.pikitori.web.repository.TagDao;
import com.pikitori.web.vo.PictureVo;
import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.TagVo;
import com.pikitori.web.vo.UserVo;

@Service
public class PostService {
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private TagDao tagDao;

	public void addPost(PostVo postVo){
		List<PictureVo> pictureList = postVo.getPictureList();
		List<TagVo> tagList = postVo.getTagList();
		
		//1. 포스트 저장
		postDao.addPost(postVo);
		//2. 사진 저장
		pictureDao.addPicture(pictureList);
		//3. 태그 저장
		tagDao.addTag(tagList);
		
		System.out.println("----------------Service postVo"+postVo);
		System.out.println("----------------Service pictureList"+pictureList);
		System.out.println("----------------Service tagList"+tagList);
		
		postDao.addAlbum(postVo);
		postDao.addPostTag(postVo);
		
	}
	
	public List<PostVo> showPostList(UserVo user){
		return postDao.showPostList(user);
	}
}
