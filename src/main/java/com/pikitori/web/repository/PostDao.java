package com.pikitori.web.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.UserVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void addPost(PostVo postVo){
		sqlSession.insert("post.addPost",postVo);
	}
	
	public void addAlbum(PostVo postVo){
		Map<String,Long> map = new HashMap<String,Long>();
		for(int i = 0; i<postVo.getPictureList().size();i++){
			map.put("post_no", postVo.getPost_no());
			map.put("picture_no",postVo.getPictureList().get(i).getPicture_no());
			sqlSession.insert("post.addAlbum",map);
		}
	}
	
	
	public void addPostTag(PostVo postVo){
		Map<String,Long> map = new HashMap<String,Long>();
		for(int i = 0; i<postVo.getTagList().size(); i++){
			map.put("post_no", postVo.getPost_no());
			map.put("tag_no", postVo.getTagList().get(i).getTag_no());
			sqlSession.insert("post.addPostTag",map);
		}
	}
	
	public boolean updatePostMovie(Long postNo, String movieurl){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("post_no", postNo);
		map.put("post_movie", movieurl);
		int count  = sqlSession.update("post.updateMovieUrl",map);
		return (count>0)? true : false;
	}
	
	public List<PostVo> showPostList(UserVo user){
		return sqlSession.selectList("post.showpostList",user);
	}
}
