package com.pikitori.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileUtils {
	
	private SecureRandom random = new SecureRandom();
	
	public String createImageDir(String path, String saveFileName) {
		int location = saveFileName.lastIndexOf('.');
		String first = saveFileName.substring(location -1 ,location);
		String second = saveFileName.substring(location -2 ,location-1);
		File firstdir = new File(path,first);
		if(! firstdir.exists()){
			firstdir.mkdirs();
		}
		File seconddir = new File(firstdir,second);
		if(!seconddir.exists() ){
			seconddir.mkdirs();
		}
//		saveFileName = first+File.separator+second+File.separator+saveFileName;
		saveFileName = first+"/"+second+"/"+saveFileName;
		return saveFileName;
	}
	
	public String writeFile(MultipartFile multipartFile, String path, String saveFileName) throws IOException {
		
		byte[] fileData = multipartFile.getBytes();
//		String dirpath = SAVE_PATH + File.separator + saveFileName;
		String dirpath = path + "/" + saveFileName;
		
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(fileData));
		ImageIO.write(bi, "png", new File(dirpath)); 
		
		return (saveFileName != null) ? saveFileName: "" ;
	}
	
	public String generateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += new BigInteger(64, random).toString(32);
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}
	
//	private static final String ffmpegPath = "/Users/admin/Downloads/source/ffmpeg-win64/ffmpeg/bin/ffmpeg.exe";
	public String convertMp4(String tmppath, String resultpath, String speed){
		String filename = generateSaveFileName("mp4");
		String fResult = resultpath+"/"+filename;
		
		File dir = new File(resultpath);
		if(!dir.exists()){
			dir.mkdir();
		}
		
//		String fOriginal = SAVE_TMP_PATH + File.separator+"image%00d"+".png"; // 실시간으로 업로드되는 파일
		String fOriginal = tmppath +"/"+"image%00d"+".png"; // 실시간으로 업로드되는 파일
		String [] cmdLine_resize = new String[]{
				"mogrify",
				"-extent",
				"1024x768",
				"-flatten",
				"-background",
				"black",
				"-gravity",
				"center",
				"-format",
				"png",
				tmppath+"/"+"*.png"
		};
//		String[] cmdLine = new String[] {
//				ffmpegPath, 
//				"-framerate",
//				"1",
//				"-f",
//				"image2",
//				"-s", "1024x768",
//				"-i", fOriginal,
//				"-q:v","0",
//				"-vcodec", "libx264",
//				"-crf", "18",
//				"-pix_fmt", "yuv420p",  
//				fResult ,"-y"};
		
		String[] cmdLine = new String[] {
				"ffmpeg", 
				"-framerate",
				"1/5",
				"-f",
				"image2",
				"-s", "1024x768",
				"-i", fOriginal,
				"-q:v","0",
				"-vcodec", "libx264",
				"-crf", "5",
				"-pix_fmt", "yuv420p",  
				fResult ,"-y"};
		
		try {
			byCommonsExec(cmdLine_resize);
			byCommonsExec(cmdLine);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	
	public void byCommonsExec(String[] command)throws IOException,InterruptedException {
	    DefaultExecutor executor = new DefaultExecutor();
	    CommandLine cmdLine = CommandLine.parse(command[0]);
	    for (int i=1, n=command.length ; i<n ; i++ ) {
	        cmdLine.addArgument(command[i]);
	    }
	    executor.execute(cmdLine);
	}

	public boolean deleteDir(String saveTmp) {
		File f = new File(saveTmp);
		if(!f.exists()){
			return false;
		}
		
		File[] files = f.listFiles();
		for( File tmp: files){
			tmp.delete();
		}
		return true;
	}

	
}
