package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.PictureVo;

@Repository
public class PictureDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void addPicture(List<PictureVo> pictureList){
	
		for(PictureVo pictureVo : pictureList){
			sqlSession.insert("picture.addPicture",pictureVo);
		}
	}
	
	public void updatePictureURL(List<PictureVo> pictureList){
		
		for(int i = 0; i<pictureList.size();i++){
			System.out.println(pictureList.get(i));
			sqlSession.insert("picture.updatePictureURL", pictureList.get(i));
		}
	}
	
	public List<PictureVo> getPictureUseingMap(PictureVo pictureVo){
		return sqlSession.selectList("picture.getpictureuseingmap",pictureVo);
		
	}
}
