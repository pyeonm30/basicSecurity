package io.test.security.controller;

import io.test.security.config.auth.PrincipalDetails;
import io.test.security.model.RoleUser;
import io.test.security.model.User;
import io.test.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "인덱스 페이지입니다.";
	}
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails=" + principalDetails.getUser());
		return "user = "+ principalDetails.getUser();
	}
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	// 스프링시큐리티의 기본 login uri로 맵핑됨!
	@GetMapping("/loginForm")
	public String login() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String join() {
		return "joinForm";
	}
	@PostMapping("/join")
	public String join(User user , String roleType) {
		System.out.println("roleType = " + roleType);
		System.out.println("user = " + user);
		user.setRole(RoleUser.valueOf(roleType.toUpperCase()));
		String initPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(initPassword);
		user.setPassword(encPassword);

		User u = userRepository.save(user);  // 이렇게 되면 시큐리티로 로그인 할수없음 => 패스워드 암호화 필수
		System.out.println("u = " + u);
		return "redirect:/loginForm";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody  String info() {
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')  ")
	@GetMapping("/userData")
	public @ResponseBody  String userData() {
		return "유저 데이터 정보 ";
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/myData")
	public @ResponseBody  String myData() {
		return "내 정보 ";
	}

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
										  @AuthenticationPrincipal UserDetails userDetails){
		System.out.println("=================");
		System.out.println("authentication=" + authentication);

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("userDetails=" + userDetails );

		return "세션 정보 확인 " + userDetails.getUsername();

	}
// Authentication authentication 객체로 받아오나
	//  @AuthenticationPrincipal 어노테이션으로 받아오나 똑같다.
	// 다만 Authentication 객체 사용할때는 다운 케스팅 필요
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testLogin(Authentication authentication,
										  @AuthenticationPrincipal OAuth2User oauth
										 ){
		System.out.println("=================");

		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("oAuth2User=" + oAuth2User.getAttributes() );
		System.out.println("oauth=" + oauth.getAttributes() );
		return "oauth 세션 정보 확인 " + oAuth2User.getAttributes();

	}





}
