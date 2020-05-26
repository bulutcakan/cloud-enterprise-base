package com.cloud.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseDTO {
	private String token;
	private String type = "Bearer";
	private UserDTO user;


	public JwtResponseDTO(String accessToken, UserDTO userDTO) {
		this.token = accessToken;
		this.user = userDTO;
	}


}
