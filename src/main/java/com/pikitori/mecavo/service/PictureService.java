package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.PictureDao;
import com.pikitori.web.vo.PictureVo;

@Service
public class PictureService {
	
	@Autowired
	private PictureDao pictureDao;
	
	public void addPicture(List<PictureVo> pictureList){
		pictureDao.addPicture(pictureList);
	}
	
	public List<PictureVo> getPictureUseingMap(PictureVo pictureVo){
		return pictureDao.getPictureUseingMap(pictureVo);
	}

}
