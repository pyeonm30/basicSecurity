package io.test.security.repository;

import io.test.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨. @Repository 안해도 됨
// 기본적은 CRUD함수를 JPA가 제공해줌
public interface UserRepository extends JpaRepository<User, Integer>{

    //findBy 까지는 규칙 -> UserName 은 조건
    // select * from user where username =?
    User findByUsername(String username);

}


