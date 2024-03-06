package io.test.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM - Object Relation Mapping

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	@Enumerated(EnumType.STRING)
	private RoleUser role; //ROLE_USER, ROLE_ADMIN

	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;

	@CreationTimestamp
	private Timestamp createDate;
}
