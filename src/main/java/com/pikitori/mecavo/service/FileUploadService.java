package com.pikitori.mecavo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.pikitori.util.FileUtils;
import com.pikitori.web.repository.PictureDao;
import com.pikitori.web.repository.PostDao;
import com.pikitori.web.vo.PictureVo;

@Repository
public class FileUploadService {
	
//	private static String domain = "http://192.168.1.4:8080/facebookSite1" ;
	private static String domain = "http://www.pikitori.com/facebookSite1" ;
	private static String image = "/piki/img";
	private static String movie = "/piki/mp4";
//	private static final String SAVE_PIKI_IMAGE = "D:\\piki";
//	private static final String SAVE_TMP = "D:\\piki_tmp";
//	private static final String SAVE_MOVIE = "D:\\piki_movie";
	private static final String SAVE_PIKI_IMAGE = "/piki";
	private static final String SAVE_TMP = "/piki_tmp";
	private static final String SAVE_MOVIE = "/piki_movie";	
	@Autowired
	private FileUtils utils;
	
	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private PostDao postDao;
	
	public String updateProfileImg(MultipartFile mpf) throws IOException {
		String orgFileName = mpf.getOriginalFilename();
		String fileExtName = orgFileName.substring( orgFileName.lastIndexOf('.') + 1, orgFileName.length() );
		String saveFileName = utils.generateSaveFileName( fileExtName );
		saveFileName = utils.createImageDir(SAVE_PIKI_IMAGE,saveFileName);
		return domain + image+ "/" + utils.writeFile(mpf, SAVE_PIKI_IMAGE,saveFileName);
	}
	
	private String tmpfile;
	private String tmpfilename = "image";
	
	public boolean makeMovie(List<MultipartFile> fileList, List<Long> pictureNoList, Long postNo, String speed) throws IOException{
		System.out.println("speed:" +speed);
		List<PictureVo> pictureList = new ArrayList<PictureVo>();
		
		//1. 사진을 upload 합니다.
		pictureList = uploadFiles(fileList,pictureNoList);

		//2. 사진 정보를 update 
		pictureDao.updatePictureURL(pictureList);
		
		//3. tmp 사진을 저장합니다.
		int i=0;
		for(MultipartFile f: fileList){
			tmpfile = tmpfilename + (i++)+ "." + "png";
			utils.writeFile(f,SAVE_TMP,tmpfile);
			tmpfile = "";
		}
		//4. 동영상 정보를 update 합니다.
		 boolean result = postDao.updatePostMovie(postNo, domain + movie+ "/" + utils.convertMp4(SAVE_TMP,SAVE_MOVIE,speed));
		
		//5. tmp 폴더를 삭제합니다.
		return result && utils.deleteDir(SAVE_TMP);
	}
	public List<PictureVo> uploadFiles(List<MultipartFile> fileList, List<Long> pictureNoList) throws IOException{
		List<PictureVo> pictureList = new ArrayList<PictureVo>();
		PictureVo[] pictureVo = new PictureVo[fileList.size()];
		
		for(int i = 0; i < fileList.size(); i++){
			pictureVo[i] = new PictureVo();
			pictureVo[i].setPicture_no(pictureNoList.get(i));
			String saveFileName = uploadFile(SAVE_PIKI_IMAGE,fileList.get(i).getOriginalFilename(), fileList.get(i));
			pictureVo[i].setPicture_url(domain + image + "/"+saveFileName);
			pictureVo[i].setPicture_ext(saveFileName.substring(saveFileName.lastIndexOf('.')+1, saveFileName.length()));
			
			System.out.println(pictureVo[i]);
			pictureList.add(pictureVo[i]);
		}
		return pictureList;
	}
	
	public String uploadFile(String path, String orgFileName, MultipartFile mpf) throws IOException {
		
		String fileExtName = orgFileName.substring( orgFileName.lastIndexOf('.') + 1, orgFileName.length() );
		String saveFileName = utils.generateSaveFileName( fileExtName );
		saveFileName = utils.createImageDir(path, saveFileName);
		return utils.writeFile(mpf, path, saveFileName);
	}

	
}
