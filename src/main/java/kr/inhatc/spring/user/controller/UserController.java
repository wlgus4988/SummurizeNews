package kr.inhatc.spring.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.service.BoardService;
import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.user.service.UserService;

@Controller
//@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String hello() {
		return "/login/login";
	}
	
	@RequestMapping("/home/user")
	public String home() {
		return "index";
	}

	@RequestMapping("/home/admin")
	public String home_admin() {
		return "/Admin/index_Admin";
	}


	@RequestMapping(value = "/login/signUp", method = RequestMethod.GET)
	public String SignUp(Model model) {
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_MEMBER");
		//authorityList.add("ROLE_ADMIN");

		Map<String, List<String>> map = new HashMap<>();
		map.put("authorityList", authorityList);

		model.addAttribute("map", map);
		return "/login/signUp";
	}

	@RequestMapping(value = "/login/signUp", method = RequestMethod.POST)
	public String signUp(Users user) {
		userService.saveUsers(user);
		// 뷰어 이동
		return "redirect:/";
	}

	@RequestMapping("/login/loginSuccess")
	public String LoginSuccess() {

		//System.out.println("================================>로그인성공");
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println("================================>" + user.getAuthorities());
		if (user.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			return "/Admin/index_Admin";
		}
		return "index";
	}
	

	@RequestMapping(value = "/user/userList/admin", method = RequestMethod.GET)
	public String userList_admin(Model model,
			@PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		// log.debug("=========================>" + "여기 !"); // 디버그로 찍는거
		Page<Users> list = userRepository.findByUsernameContainingOrNameContaining(searchText, searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);

		return "Admin/userList_Admin";
	}

	@RequestMapping("/user/test")
	public String userTest() {
		return "/user/test";
	}

	@RequestMapping("/user/test2")
	public String Help() {
		return "/user/test2";
	}

	// GET(읽을때), POST(만들때), PUT(업데이트할때), DELETE(삭제할때) /@GetMapping 등등 붙여서 쓸수도 있음
	// @GetMapping("/userList") // 위에 RequestMapping과 연결됨
	@RequestMapping(value = "/user/userList", method = RequestMethod.GET)
	public String userList(Model model, @PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		Page<Users> list = userRepository.findByUsernameContainingOrNameContaining(searchText, searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);
		return "user/userList";
	}

	// @GetMapping("/userInsert") // 위에 RequestMapping과 연결됨
	@RequestMapping(value = "/user/userInsert", method = RequestMethod.GET)
	public String userWrite(Model model) {
		// 뷰어 이동

		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");

		Map<String, List<String>> map = new HashMap<>();
		map.put("authorityList", authorityList);

		model.addAttribute("map", map);

		return "user/userWrite";
	}

	// @GetMapping("/userInsert") // 위에 RequestMapping과 연결됨
	@RequestMapping(value = "/user/userInsert", method = RequestMethod.POST)
	public String userInsert(Users user) {
		if (user != null) {
//			System.out.println("변경 전 : "+ user.getPw());
			String pw = encoder.encode(user.getPw());
//			System.out.println("변경 후 : "+ pw);
			user.setPw(pw);
			userService.saveUsers(user);
		}
		// 뷰어 이동
		return "redirect:/user/userList";
	}
	
	// @GetMapping("/userInsert") // 위에 RequestMapping과 연결됨
		@RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.GET)
		public String userWrite_admin(Model model) {
			// 뷰어 이동

			List<String> authorityList = new ArrayList<>();
			authorityList.add("ROLE_MEMBER");
			authorityList.add("ROLE_ADMIN");

			Map<String, List<String>> map = new HashMap<>();
			map.put("authorityList", authorityList);

			model.addAttribute("map", map);

			return "/Admin/userWrite_Admin";
		}

		// @GetMapping("/userInsert") // 위에 RequestMapping과 연결됨
		@RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.POST)
		public String userInsert_admin(Users user) {
			if (user != null) {
//				System.out.println("변경 전 : "+ user.getPw());
				String pw = encoder.encode(user.getPw());
//				System.out.println("변경 후 : "+ pw);
				user.setPw(pw);
				userService.saveUsers(user);
			}
			// 뷰어 이동
			return "redirect:/user/userList/admin";
		}
		

	// = @GetMapping
	@RequestMapping(value = "/user/userDetail/{username}", method = RequestMethod.GET) // userList.html에서 받아온 id값
	public String userDetail(@PathVariable("username") String username, Model model) {
		Users user = userService.userDetail(username);
		model.addAttribute("user", user); // 웹에서 불러온 user, 참조변수
		// 뷰어 이동
		return "user/userDetail";
	}
	// = @GetMapping
	@RequestMapping(value = "/user/userDetail/admin/{username}", method = RequestMethod.GET) // userList.html에서 받아온 id값
	public String userDetail_admin(@PathVariable("username") String username, Model model) {
		Users user = userService.userDetail(username);
		model.addAttribute("user", user); // 웹에서 불러온 user, 참조변수
		// 뷰어 이동
		return "/Admin/userDetail_Admin";
	}

	@RequestMapping(value = "/user/userUpdate/{username}", method = RequestMethod.POST) // userList.html에서 받아온 id값
	public String userUpdate(@PathVariable("username") String username, Users user) {

		user.setUsername(username);

		userService.saveUsers(user);
		return "redirect:/user/userList";
	}
	@RequestMapping(value = "/user/userUpdate/admin/{username}", method = RequestMethod.POST) // userList.html에서 받아온 id값
	public String userUpdate_admin(@PathVariable("username") String username, Users user) {

		user.setUsername(username);

		userService.saveUsers(user);
		return "redirect:/user/userList/admin";
	}

	@RequestMapping(value = "/user/userDelete/{username}", method = RequestMethod.GET)
	public String userDelete(@PathVariable("username") String username) {
		userService.userDelete(username);
		return "redirect:/user/userList";
	}
	@RequestMapping(value = "/user/userDelete/admin/{username}", method = RequestMethod.GET)
	public String userDelete_admin(@PathVariable("username") String username) {
		userService.userDelete(username);
		return "redirect:/user/userList/admin";
	}

}
