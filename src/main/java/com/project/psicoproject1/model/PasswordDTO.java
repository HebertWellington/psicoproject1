package com.project.psicoproject1.model;

import com.project.psicoproject1.config.ValidPassword;

public class PasswordDTO {
	
	    private String oldPassword;

	    private  String token;

	    @ValidPassword
	    private String newPassword;

		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		
		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
	

}

