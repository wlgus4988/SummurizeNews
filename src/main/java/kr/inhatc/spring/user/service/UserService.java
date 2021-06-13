package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;

public interface UserService {

	List<Users> userList();

	void saveUsers(Users user);

	Users userDetail(String username);

	void userDelete(String username);
	
	void saveFiles(Users user, MultipartHttpServletRequest multipartHttpServletRequest);
	
	Page<Users> userPageList(String searchText, Pageable pageable);

}
