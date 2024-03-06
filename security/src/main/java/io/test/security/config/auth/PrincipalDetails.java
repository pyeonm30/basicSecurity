package io.test.security.config.auth;

import io.test.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Authentication 객체에 저장할 수 있는 유일한 타입
public class PrincipalDetails implements UserDetails{

	private User user;

	// 일반 시큐리티 로그인시 사용
	public PrincipalDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}


	// 해당 user의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return String.valueOf(user.getRole());
			}
		});
		return  collect;
	}

	// 너 계정 아직 만료 안됬니? 응
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 너 걔정 안 잠겼니? 응
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
// 너 계정 사용기간 안지났니? 응
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 너 걔정 활성화니? 응
	@Override
	public boolean isEnabled() {

		// 회원이 1년동안 로그인 안하면 휴면 계정 되는 것
		// 현재시간 -  로그인시간 이런 것들..

		return true;
	}
	
}
