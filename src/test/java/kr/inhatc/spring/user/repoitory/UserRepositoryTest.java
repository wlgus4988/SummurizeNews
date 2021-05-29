package kr.inhatc.spring.user.repoitory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

// 테스트
@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void inserTest() {
		Users user = Users.builder() // 객체 생성
				.username("abc")
				.pw("111")
				.email("abc@test.com")
				.role("ROLE_ADMIN")
				.build(); 
		
		userRepository.save(user);
	}
	
	@Test
	void findAllByOrderByUserIdDescTest() {
		List<Users> list = userRepository.findAllByOrderByUsernameDesc();
		System.out.println("========================Test========================");
		for (Users users : list) {
			System.out.println(users);
		}
	}

}
