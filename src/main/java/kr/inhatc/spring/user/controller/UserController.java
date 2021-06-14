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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.service.BoardService;
import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.user.entity.FileDto;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.FileRepository;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.user.service.FileService;
import kr.inhatc.spring.user.service.UserService;
import kr.inhatc.spring.utils.FileUtils;

@Controller
//@RequestMapping("/user")
public class UserController {

	
	public String path;
	public String OCR;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;

	@RequestMapping("/")
	public String hello() {
		return "/login/login";
	}
	
	@RequestMapping("/user/test2")
	public String member(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "/user/test2";
	}

	@RequestMapping("/home/user")
	public String home(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "index";
	}

	@RequestMapping("/home/user/return")
	public String home_return(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "redirect:/login/loginSuccess";
	}

	@RequestMapping("/home/admin")
	public String home_admin() {
		return "/Admin/index_Admin";
	}

	@RequestMapping(value = "/login/signUp", method = RequestMethod.GET)
	public String SignUp(Model model) {
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_MEMBER");
		// authorityList.add("ROLE_ADMIN");

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
	public String LoginSuccess(Model model) {

		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		if (user.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			return "/Admin/index_Admin";
		}
		return "index";
	}

	@RequestMapping(value = "/user/userList/admin", method = RequestMethod.GET)
	public String userList_admin(Model model, @PageableDefault(size = 2) Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		// log.debug("=========================>" + "여기 !"); // 디버그로 찍는거
		Page<Users> list = userService.userPageList(searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);

		return "Admin/userList_Admin";
	}

	@RequestMapping(value = "/user/test", method = { RequestMethod.GET, RequestMethod.POST })
	public String userTest(Model model, HttpServletRequest req) throws IOException {

		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		String content = req.getParameter("content");

		String command = "python tests.py" + " " + content;

		Process child = Runtime.getRuntime().exec(command);

		String str = "";

		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {

			ArrayList<Character> arrays = new ArrayList<Character>();
			arrays.add((char) c);

			for (Character array : arrays) {
				str += array;
			}
			if (str.equals("null")) {
				str = " ";
				// System.out.println("============>="+str);
			}
		}

		model.addAttribute("text", str);

		in.close();

		return "/user/test";
	}
	
	@RequestMapping(value = "/test/maketext", method = { RequestMethod.GET, RequestMethod.POST })
	public String makeText(Model model, HttpServletRequest req) throws IOException {

		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		String content = req.getParameter("contents");
		
		System.out.println("==================="+content);

		String command = "python test_KoGPT2.py" + " " + content;

		Process child = Runtime.getRuntime().exec(command);

		String str = " ";

		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {

			ArrayList<Character> arrays = new ArrayList<Character>();
			arrays.add((char) c);

			for (Character array : arrays) {
				str += array;
			}

		}

		model.addAttribute("make", str);

		System.out.println("====================>"+str);
		in.close();

		return "/test/maketext";
	}
	

	@RequestMapping(value = "/test/textview", method = RequestMethod.GET)
	public String text(Model model) {

		//System.out.println("===================================>시발");

		SecurityUser users = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", users.getUsername());

		
		return "/test/textview";
	}
	
//	@RequestMapping(value = "/test/textview_", method = RequestMethod.GET)
//	public String text_return(Model model) {
//
//		//System.out.println("===================================>시발");
//
//		SecurityUser users = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		model.addAttribute("username", users.getUsername());
//		
//		return "redirect:/test/textview";
//	}

	@RequestMapping(value="/test/textview", method = RequestMethod.POST)
	public String textTest(Model model, Users user, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException{
			
		SecurityUser users = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", users.getUsername());

		userService.saveFiles(user, multipartHttpServletRequest);
		
		
		List<FileDto> files = fileService.fileList();
		path = files.get(1).getStoredFilePath();
		
		String message = "C:/Users/USER/Capston/SummurizeNews/src/main/resources/static" + path;
		
		System.out.println("============================>"+path);
		
		String command = "python image_test.py" + " " + message +" "+"stdout -l kor";

		Process child = Runtime.getRuntime().exec(command);

		String str = "";
		
		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {
			
			ArrayList<Character> arrays = new ArrayList<Character>();
			arrays.add((char) c);

			
			for (Character array : arrays) {
				str += array;
			}
		}
		System.out.println("====================>str"+str);
		
		OCR = str;
		
		in.close();
		
		return "redirect:/test/textview_ocr";
	}
	
	@RequestMapping(value = "/test/textview_ocr", method = RequestMethod.GET)
	public String text_OCR(Model model) {

		SecurityUser users = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", users.getUsername());
		
		System.out.println("================>"+OCR);
		model.addAttribute("OCR", OCR);

		return "/test/textview";
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

//	// = @GetMapping
//	@RequestMapping("/user/userDetail")
//	public String userDetail(Model model) {
//		SecurityUser users = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		//Users user = userService.userList(user);
//		List<Users> user = userService.userList();
//		((Users) user).setUsername(users.getUsername());
//		// 뷰어 이동
//		return "redirect:/user/userDetail/{username}";
//	}

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
		return "redirect:/user/userDetail/{username}";
	}

	@RequestMapping(value = "/user/userUpdate/admin/{username}", method = RequestMethod.POST) // userList.html에서 받아온 id값
	public String userUpdate_admin(@PathVariable("username") String username, Users user) {

		user.setUsername(username);

		userService.saveUsers(user);
		return "redirect:/user/userList/admin";
	}

//	@RequestMapping(value = "/user/userDelete/{username}", method = RequestMethod.GET)
//	public String userDelete(@PathVariable("username") String username) {
//		userService.userDelete(username);
//		return "redirect:/user/userList";
//	}

	@RequestMapping(value = "/user/userDelete/admin/{username}", method = RequestMethod.GET)
	public String userDelete_admin(@PathVariable("username") String username) {
		userService.userDelete(username);
		return "redirect:/user/userList/admin";
	}

}
