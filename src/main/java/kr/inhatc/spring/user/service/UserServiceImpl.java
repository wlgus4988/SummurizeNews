package kr.inhatc.spring.user.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.FileRepository;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;

@Service
public class UserServiceImpl implements UserService { 
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private FileUtils fileUtils;
	

	@Override
	public Page<Users> userPageList(String searchText, Pageable pageable) {
		Page<Users> list = userRepository.findByUsernameContainingOrNameContaining(searchText, searchText, pageable);
		return list;
	}
	
	
	@Override
	public List<Users> userList() {
		
		// Optional<Users> result = userRepository.findById("ab"); // userRepository 많이 찾아보기
		
		List<Users> list = userRepository.findAllByOrderByUsernameDesc(); // findAllBy:모든레코드잡아오기/OrderBy/UserId/Desc
		//System.out.println("========================================= 크기 : " + list.size());
		return list;
	}

	@Override
	public void saveUsers(Users user) {
		
		String username = user.getUsername();
		user.setUsername(username);
		

		
		userRepository.save(user);
	}

	@Override
	public Users userDetail(String username) {
		Optional<Users> optional = userRepository.findById(username);
		if(optional.isPresent()) {// id가 존재한다면
			Users user = optional.get();
			return user;
		} else { // 없을때는
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String username) {
		userRepository.deleteById(username);
	}
	
	@Override
	public void saveFiles(Users user,MultipartHttpServletRequest multipartHttpServletRequest) {
		//fileRepository.save(username);
		
         //파일 임시확인 
      if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//          System.out.println("여기 eHLD");
      	Iterator<String> iter = multipartHttpServletRequest.getFileNames();
//          System.out.println("읎냐? : "+ iter.hasNext());
          // 다음 내용이 있는지 확인
          while(iter.hasNext()) {
              String name = iter.next();
              System.out.println("-----------*******>" + name);
              List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//              for(MultipartFile multipartFile : list) {
//                  System.out.println("==============> file name : "+ multipartFile.getOriginalFilename());
//                  System.out.println("==============> file size : "+ multipartFile.getSize());
//                  System.out.println("==============> file type : "+ multipartFile.getContentType());
//              }
          }
      }
      // 1.파일저장
      List<kr.inhatc.spring.user.entity.FileDto> list = fileUtils.parseFileInfo(user, multipartHttpServletRequest);
     // System.out.println("nani~~~~~ : "+ list);
//      //2. db 저장
//      if(CollectionUtils.isEmpty(list) == false) {
//    	  fileRepository.saveAll(list);
//		
//      }
	}
	
}
