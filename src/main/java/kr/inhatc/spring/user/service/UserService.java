package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.inhatc.spring.user.entity.Users;

public interface UserService {

	List<Users> userList();

	void saveUsers(Users user);

	Users userDetail(String username);

	void userDelete(String username);
	
	Page<Users> userPageList(String searchText, Pageable pageable);

}
