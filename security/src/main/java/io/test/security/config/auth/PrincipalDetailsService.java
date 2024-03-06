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
			return createUserDetails(user);
		}
		
	}


	private UserDetails createUserDetails(User user) {

		String role = user.getRole().toString();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role);
		return new org.springframework.security.core.userdetails.User(
				String.valueOf(user.getUsername()),
				user.getPassword(),
				Collections.singleton(grantedAuthority)
		);
	}

}
