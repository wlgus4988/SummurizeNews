package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService { 
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<Users> userPageList(String searchText, Pageable pageable) {
		
		Page<Users> list = userRepository.findByUsernameContainingOrNameContaining(searchText, searchText, pageable);
		
		return list;
	}
	
	
	@Override
	public List<Users> userList() {
		
		List<Users> list = userRepository.findAllByOrderByUsernameDesc(); // findAllBy:모든레코드잡아오기/OrderBy/Username/Desc
		
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
		
		if(optional.isPresent()) { // id가 존재한다면
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
	
}
