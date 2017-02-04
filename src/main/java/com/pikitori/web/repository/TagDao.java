package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.TagVo;

@Repository
public class TagDao {

	@Autowired
	private SqlSession sqlSession;

	public void addTag(List<TagVo> tagList) {

		if (tagList.size() != 0) {
			for(TagVo tagVo : tagList){
				sqlSession.insert("tag.addTag",tagVo);
			}
		}
	}
	
	public List<TagVo> tagPostList(String str){
		return sqlSession.selectList("tag.searchTagPostList", str);
	}
}
