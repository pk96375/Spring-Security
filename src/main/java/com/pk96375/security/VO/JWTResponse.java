package com.pk96375.security.VO;

public class JWTResponse {

	private String token;

	public JWTResponse() {
	}

	public JWTResponse(String token) {
		this.token = token;
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
