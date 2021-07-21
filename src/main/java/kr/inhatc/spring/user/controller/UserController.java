package kr.inhatc.spring.user.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.service.UserService;

@Controller
//@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserService userService;

	// 로그인
	@RequestMapping("/")
	public String hello() {
		return "/login/login";
	}

	// 회원가입
	@RequestMapping(value = "/login/signUp", method = RequestMethod.GET)
	public String SignUp(Model model) {
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_MEMBER");

		Map<String, List<String>> map = new HashMap<>();
		map.put("authorityList", authorityList);

		model.addAttribute("map", map);
		return "/login/signUp";
	}

	@RequestMapping(value = "/login/signUp", method = RequestMethod.POST)
	public String signUp(Users user) {

		if (user != null) {
			String pw = encoder.encode(user.getPw());
			user.setPw(pw);
			userService.saveUsers(user);
		}

		// 뷰어 이동
		return "redirect:/";
	}

	// 로그인 성공 시
	@RequestMapping("/login/loginSuccess")
	public String LoginSuccess(Model model) {

		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		if (user.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			return "/Admin/index_Admin";
		}
		return "index";
	}

	@RequestMapping("/home/user/return")
	public String home_return(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "redirect:/login/loginSuccess";
	}

//	@RequestMapping("/home/user")
//	public String home(Model model) {
//		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		model.addAttribute("username", user.getUsername());
//		return "index";
//	}
//	@RequestMapping("/home/admin")
//	public String home_admin() {
//		return "/Admin/index_Admin";
//	}

	
	// 사용자 회원 정보 ===================================================================================================================

	// 사용자 상세 정보
	@RequestMapping(value = "/user/userDetail/{username}", method = RequestMethod.GET) // userList.html에서 받아온 id값
	public String userDetail(@PathVariable("username") String username, Model model) {
		Users user = userService.userDetail(username);
		model.addAttribute("user", user); // 웹에서 불러온 user, 참조변수
		// 뷰어 이동
		return "user/userDetail";
	}

	// 사용자 정보 수정
	@RequestMapping(value = "/user/userUpdate/{username}", method = RequestMethod.POST) // userList.html에서 받아온 id값
	public String userUpdate(@PathVariable("username") String username, HttpServletRequest req, Model model,
			Users user) {

		user.setUsername(username);
		user.setRole("ROLE_MEMBER");

		String password = encoder.encode(req.getParameter("pw"));
		if (!user.getPw().equals(password)) {
			user.setPw(password);
		}

		model.addAttribute("user", user);
		userService.saveUsers(user);
		return "redirect:/user/userDetail/{username}";
	}

	// 개발자 소개
	@RequestMapping("/user/developer")
	public String member(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "/user/developer";
	}

//		@RequestMapping(value = "/user/userInsert", method = RequestMethod.GET)
//		public String userWrite(Model model) {
//			// 뷰어 이동

//			List<String> authorityList = new ArrayList<>();
//			authorityList.add("ROLE_MEMBER");
//			authorityList.add("ROLE_ADMIN");

//			Map<String, List<String>> map = new HashMap<>();
//			map.put("authorityList", authorityList);

//			model.addAttribute("map", map);

//			return "user/userWrite";
//		}

//		@RequestMapping(value = "/user/userInsert", method = RequestMethod.POST)
//		public String userInsert(Users user) {
//			if (user != null) {
//				String pw = encoder.encode(user.getPw());
//				user.setPw(pw);
//				userService.saveUsers(user);
//			}
//			// 뷰어 이동
//			return "redirect:/user/userList";
//		}
//	@RequestMapping(value = "/user/userList", method = RequestMethod.GET)
//	public String userList(Model model, @PageableDefault(size = 2) Pageable pageable,
//			@RequestParam(required = false, defaultValue = "") String searchText) {
//		// log.debug("=========================>" + "여기 !"); // 디버그로 찍는거
//		Page<Users> list = userService.userPageList(searchText, pageable);
//
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("list", list);
//
//		return "user/userList";
//	}

	
	
	// 관리자 회원 정보 관리 ===================================================================================================================

	// 회원 리스트
	@RequestMapping(value = "/user/userList/admin", method = RequestMethod.GET)
	public String userList_admin(Model model, @PageableDefault(size = 2) Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {

		Page<Users> list = userService.userPageList(searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		model.addAttribute("list", list);

		return "Admin/userList_Admin";
	}

	// 사용자 추가
	@RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.GET)
	public String userWrite_admin(Model model) {

		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");

		Map<String, List<String>> map = new HashMap<>();
		map.put("authorityList", authorityList);

		model.addAttribute("map", map);

		return "/Admin/userWrite_Admin";
	}
	@RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.POST)
	public String userInsert_admin(Users user) {
		if (user != null) {
			String pw = encoder.encode(user.getPw());
			user.setPw(pw);
			userService.saveUsers(user);
		}
		// 뷰어 이동
		return "redirect:/user/userList/admin";
	}
	
	// 회원 상세 정보
	@RequestMapping(value = "/user/userDetail/admin/{username}", method = RequestMethod.GET) // userList.html에서 받아온 id값
	public String userDetail_admin(@PathVariable("username") String username, Model model) {
		Users user = userService.userDetail(username);
		model.addAttribute("user", user); // 웹에서 불러온 user, 참조변수
		// 뷰어 이동
		return "/Admin/userDetail_Admin";
	}

	// 회원 정보 수정
	@RequestMapping(value = "/user/userUpdate/admin/{username}", method = RequestMethod.POST) // userList.html에서 받아온 id값
	public String userUpdate_admin(@PathVariable("username") String username, HttpServletRequest req, Users user) {

		user.setUsername(username);

		String password = encoder.encode(req.getParameter("pw"));
		if (!user.getPw().equals(password)) {
			user.setPw(password);
		}

		userService.saveUsers(user);
		return "redirect:/user/userList/admin";
	}

	// 회원 삭제
	@RequestMapping(value = "/user/userDelete/admin/{username}", method = RequestMethod.GET)
	public String userDelete_admin(@PathVariable("username") String username) {
		userService.userDelete(username);
		return "redirect:/user/userList/admin";
	}

}
