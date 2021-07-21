package kr.inhatc.spring.text.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.text.entity.Texts;
import kr.inhatc.spring.user.entity.Users;

public interface TextService {

	void saveTexts(Texts text);
	
	void saveFiles(Users user, MultipartHttpServletRequest multipartHttpServletRequest);

}
