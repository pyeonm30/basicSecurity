package io.test.security.controller;

import io.test.security.model.RoleUser;
import io.test.security.model.User;
import io.test.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public @ResponseBody String user() {
		return "user";
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



}
