package com.pikitori.mecavo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pikitori.mecavo.service.FileUploadService;
import com.pikitori.mecavo.service.UserService;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/mecavo/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	FileUploadService fileUploadService;

	@RequestMapping("/list")
	@ResponseBody
	public Object check() {
		List<UserVo> list = userService.getAllUsers();
		return JSONResult.success(list);
	}

	@RequestMapping("/checkemail")
	@ResponseBody
	public JSONResult checkEmail(@RequestParam(value = "user_id", required = true) String user_id) {
		boolean result = userService.checkEmail(user_id);
		return JSONResult.success(result ? "exist" : "not exist");
	}

	// @RequestMapping("/checknickname")
	// @ResponseBody
	// public JSONResult checkNickname(@RequestParam(value = "user_name",
	// required = true) String user_name) {
	// boolean result = userService.checkNickname(user_name);
	// return JSONResult.success(result ? "exist" : "not exist");
	//
	// }

	@RequestMapping("/login")
	@ResponseBody
	public JSONResult Login(@RequestBody UserVo user, HttpSession session) {
		if (user != null) {
			System.out.println(user);
			UserVo resultUser = userService.login(user);
			session.setAttribute("authUser", resultUser);
			System.out.println("login: " + resultUser);
			System.out.println(session.getId());
			if(resultUser !=null){
				return JSONResult.success(resultUser);
			}
		}
		return JSONResult.error(null);
	}

	@RequestMapping("/createuser")
	@ResponseBody
	public JSONResult createUser(@RequestBody @Valid UserVo user, BindingResult validation) {
		System.out.println(user);
		if (validation.hasErrors()) {
			System.out.println("error");
			return JSONResult.error(validation.getErrorCount() + "개의 에러가 발생하여 회원가입을 완료하지 못하였습니다.");
		}
		boolean result = userService.addUser(user);
		return JSONResult.success(result ? "create" : "not create");
	}

	@RequestMapping("/createf_user")
	@ResponseBody
	public JSONResult createf_User(@RequestBody UserVo user, HttpSession session, BindingResult validation) {
		System.out.println(user);
		if (validation.hasErrors()) {
			return JSONResult.error(validation.getErrorCount() + "개의 에러가 발생하여 회원가입을 완료하지 못하였습니다.");
		}
		UserVo resultUser = userService.addF_User(user);
		session.setAttribute("authUser", resultUser);
		return JSONResult.success(resultUser);
	}

	@RequestMapping("/update_profile_img")
	@ResponseBody
	public JSONResult update_profile_img(@ModelAttribute UserVo user, MultipartFile file, HttpSession session,
			BindingResult validation) throws IOException {
		System.out.println(user);

		// 1. validation 체크
		if (validation.hasErrors()) {
			return JSONResult.error(validation.getErrorCount() + "개의 에러가 발생하여 프로필 수정을  완료하지 못하였습니다.");
		}

		// 2. session 체크
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return JSONResult.error("RequestSession");
		}

		if (file != null) {
			// 3. 파일저장
			String filepath = fileUploadService.updateProfileImg(file);
			System.out.println("img save: " + filepath);

			user.setUser_profile_url(filepath);
			System.out.println(user);
		}
		// 4. update profile info
		UserVo result = userService.updateProfile(user);
		System.out.println(result);
		return JSONResult.success(result);
	}

	@RequestMapping("/update_profile")
	@ResponseBody
	public JSONResult update_profile(@RequestBody UserVo user, HttpSession session, BindingResult validation)
			throws IOException {
		System.out.println(user);

		// 1. validation 체크
		if (validation.hasErrors()) {
			return JSONResult.error(validation.getErrorCount() + "개의 에러가 발생하여 프로필 수정을  완료하지 못하였습니다.");
		}

		// 2. session 체크
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return JSONResult.error("RequestSession");
		}

		// 4. update profile info
		UserVo result = userService.updateProfile(user);
		System.out.println(result);
		return JSONResult.success(result);
	}

	// @RequestMapping("/update_profile_img")
	// @ResponseBody
	// public JSONResult update_profile_img(@RequestBody UserVo user,
	// MultipartFile file, HttpSession session) throws IOException {
	// System.out.println(user);
	//
	// // 1. session 체크
	// UserVo authUser = (UserVo) session.getAttribute("authUser");
	// if (authUser == null) {
	// return JSONResult.error("RequestSession");
	// }
	// //2. 파일저장
	// String filepath = fileUploadService.updateProfileImg(file);
	// System.out.println("img save: "+ filepath);
	// //3. db update
	// boolean result = userService.updateProfileImg(filepath,user);
	// System.out.println("updated: " + result);
	// return JSONResult.success(result? user.getUser_no(): -1);
	// }
	//
	@RequestMapping("/get_user")
	@ResponseBody
	public JSONResult getUser(@RequestBody UserVo user, HttpSession session) {
		System.out.println(user);
		// 1. session 체크
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return JSONResult.error("RequestSession");
		}
		UserVo resultUser = userService.getUser(user.getUser_no());
		session.setAttribute("authUser", resultUser);
		System.out.println(session.getId());
		return JSONResult.success(resultUser);
	}
	
	@RequestMapping("/searchname")
	@ResponseBody
	public Object searchNameList(@RequestParam String str) {
		List<UserVo> list = userService.searchNameList(str);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);
			
		return map;
	}

}
