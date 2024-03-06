package io.test.security.config.auth;

import io.test.security.model.User;
import io.test.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// 시큐리티 설정해서 loginProcessingUrl("/login") 으로 /login 요청이 오면
// UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername() 함수를 자동으로 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	// 시큐리티 session (Authentication ( 내부 UserDetails)))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}else {
			return new PrincipalDetails(user);  // 이 함수 종료할때 @AuthenticationProncpal 어노테이션의 객체가 만들어진다
		}
		
	}




}
