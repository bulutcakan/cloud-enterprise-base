package com.cloud.base.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.netty.handler.codec.http.multipart.FileUpload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@Column
	@Size(min = 2, max = 20)
	private String surname;

	@Column
	private Date birthDate;

	@Column
	@NotBlank
	@Size(min = 1, max = 20)
	private String phoneNumber;

	@Column
	@Max(10000)
	@Min(1)
	private Long feePerHour;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(unique = true)
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;



	@Column(columnDefinition="BOOLEAN DEFAULT false")
	private Boolean active;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();



	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_education_id", nullable = false)
	private Education education;


	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_special_id", nullable = false)
	private Specialties specialties;

	@OneToMany(mappedBy = "user")
	private Set<UploadedFiles> uploadedFiles;
}
