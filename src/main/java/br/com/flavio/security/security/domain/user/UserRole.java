package br.com.flavio.security.security.domain.user;

public enum UserRole {
	
	ADMIM("ADMIN"),
	USER("user");
	
	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
 
	 
}
