package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;

@Service
public class UserServiceImpl implements UserService { 
	
	@Autowired
	UserRepository userRepository;
	

	
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
	public Users userDetail(String userId) {
		Optional<Users> optional = userRepository.findById(userId);
		if(optional.isPresent()) {// id가 존재한다면
			Users user = optional.get();
			return user;
		} else { // 없을때는
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String userId) {
		userRepository.deleteById(userId);
	}
	
}
