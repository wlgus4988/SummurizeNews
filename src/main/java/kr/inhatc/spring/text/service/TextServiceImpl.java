package kr.inhatc.spring.text.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.text.entity.Texts;
import kr.inhatc.spring.text.entity.FileDto;
import kr.inhatc.spring.text.repository.TextRepository;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.text.repository.FileRepository;
import kr.inhatc.spring.utils.FileUtils;
import kr.inhatc.spring.login.security.SecurityUser;


@Service
public class TextServiceImpl implements TextService {

	@Autowired
	TextRepository textRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public void saveTexts(Texts text) {
		
		SecurityUser user = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		text.setUsername(user.getUsername());		

		textRepository.save(text);
	}

	@Override
	public void saveFiles(Users user,MultipartHttpServletRequest multipartHttpServletRequest) {
		
         //파일 임시확인 
      if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
    	  
      	Iterator<String> iter = multipartHttpServletRequest.getFileNames();
      	
          // 다음 내용이 있는지 확인
          while(iter.hasNext()) {
              String name = iter.next();
              List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
          }
      }
      
      // 1.파일저장
      List<FileDto> list = fileUtils.parseFileInfo(user, multipartHttpServletRequest);

//      // 2. db 저장
//      if(CollectionUtils.isEmpty(list) == false) {
//    	  fileRepository.saveAll(list);
//		
//     }
	}

}