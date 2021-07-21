package kr.inhatc.spring.text.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.text.service.TextService;
import kr.inhatc.spring.text.entity.FileDto;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.text.service.FileService;
import kr.inhatc.spring.text.entity.Texts;
import kr.inhatc.spring.login.security.SecurityUser;

@Controller
public class TextController {

	@Autowired
	private TextService textService;

	@Autowired
	private FileService fileService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public String path;
	public String OCR;

	
	// 텍스트 요약 ============================================================================================
	
	@RequestMapping(value = "/text/article_summary", method = { RequestMethod.GET, RequestMethod.POST })
	public String article_summary(HttpServletRequest req, Model model, Texts text) throws IOException {
		
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		String content = req.getParameter("content");

		// cmd 명령어
		String command = "python test.py" + " " + content;

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
		
		text.setOrignalContents(content);
		text.setSummaryContents(str);

		if (content != null) {
			textService.saveTexts(text);
		}
		
		in.close();

		model.addAttribute("result", str);

		return "/text/article_summary";
	}

	
	// 맞춤법 검사 ============================================================================================
	
	@RequestMapping(value = "/text/spell_check", method = { RequestMethod.GET, RequestMethod.POST })
	public String spell_check(Model model, HttpServletRequest req) throws IOException {
		
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
			}
		}

		in.close();
		
		model.addAttribute("text", str);

		return "/text/spell_check";
	}
	
	
	// 문장 생성 =============================================================================================

	@RequestMapping(value = "/text/sentence_generation", method = { RequestMethod.GET, RequestMethod.POST })
	public String sentence_generation(Model model, HttpServletRequest req) throws IOException {
		
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		String str = " ";
		
		String content = req.getParameter("content");

		if (content != null) {
			String command = "python test_KoGPT2.py" + " " + content;

			Process child = Runtime.getRuntime().exec(command);

			str = " ";
			InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
			int c = 0;

			while ((c = in.read()) != -1) {
				ArrayList<Character> arrays = new ArrayList<Character>();
				arrays.add((char) c);
				for (Character array : arrays) {
					str += array;
				}
			}
			
			in.close();

		} else if (content == null) {

			str = " ";
		}

		model.addAttribute("make", str);

		return "/text/sentence_generation";
	}
	
	
	// 텍스트 추출 ============================================================================================

	@RequestMapping(value = "/text/extract_text", method = RequestMethod.GET)
	public String text(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		return "/text/extract_text";
	}

	@RequestMapping(value = "/text/extract_text", method = RequestMethod.POST)
	public String extract_text(Model model, Users users, MultipartHttpServletRequest multipartHttpServletRequest)
			throws IOException {

		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());

		textService.saveFiles(users, multipartHttpServletRequest);

		List<FileDto> files = fileService.fileList();
		path = files.get(1).getStoredFilePath();

		String message = "C:/Users/USER/Capston/SummurizeNews/src/main/resources/static" + path;

		String command = "python image_test.py" + " " + message + " " + "stdout -l kor";

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

		OCR = str;

		in.close();

		return "redirect:/text/extract_text_ocr";
	}

	@RequestMapping(value = "/text/extract_text_ocr", method = RequestMethod.GET)
	public String text_OCR(Model model) {
		
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		model.addAttribute("OCR", OCR);

		return "/text/extract_text";
	}

}
