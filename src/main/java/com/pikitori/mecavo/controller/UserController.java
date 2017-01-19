package com.pikitori.mecavo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.UserService;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/mecavo/user")
public class UserController {

	@Autowired
	UserService userService;

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

//	@RequestMapping("/checknickname")
//	@ResponseBody
//	public JSONResult checkNickname(@RequestParam(value = "user_name", required = true) String user_name) {
//		boolean result = userService.checkNickname(user_name);
//		return JSONResult.success(result ? "exist" : "not exist");
//
//	}

	@RequestMapping("/login")
	@ResponseBody
	public JSONResult Login(@ModelAttribute UserVo user, HttpSession session) {
		System.out.println(user);
		UserVo resultUser = userService.login(user);
		session.setAttribute("authUser", resultUser);
		return JSONResult.success(resultUser);
	}

	@RequestMapping("/createuser")
	@ResponseBody
	public JSONResult createUser(@ModelAttribute @Valid UserVo user, BindingResult validation ) {
		System.out.println(user);
		if(validation.hasErrors()){
			return JSONResult.error(validation.getErrorCount()+"개의 에러가 발생하여 회원가입을 완료하지 못하였습니다.");
		}
		boolean result = userService.addUser(user);
		return JSONResult.success(result ? "create" : "not create");
	}

	@RequestMapping("/createf_user")
	@ResponseBody
	public JSONResult createf_User(@ModelAttribute UserVo user, HttpSession session, BindingResult validation) {
		System.out.println(user);
		if(validation.hasErrors()){
			return JSONResult.error(validation.getErrorCount()+"개의 에러가 발생하여 회원가입을 완료하지 못하였습니다.");
		}
		UserVo resultUser = userService.addF_User(user);
		session.setAttribute("authUser", resultUser);
		return JSONResult.success(resultUser);
	}

}
