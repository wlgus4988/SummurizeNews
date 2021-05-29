package kr.inhatc.spring.login.security;

import java.util.Collection;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import kr.inhatc.spring.user.entity.Users;

public class SecurityUser extends User {
	
	private Users user;

	public SecurityUser(Users user) {
		
		//암호화 처리 전까지는 패스워드 앞에 {noop} 추가
		//암호화 호에는 {noop} 지우기
		super(user.getUsername(), "{noop}"+user.getPw(), AuthorityUtils.createAuthorityList(user.getRole()));
		this.user = user;
	}	
}
