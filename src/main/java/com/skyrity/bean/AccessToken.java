package com.skyrity.bean;

import java.util.Date;

/**
 * 定义令牌类
 */
public class AccessToken {

	    // 获取到的凭证
		private String token;
		// 凭证有效时间，单位：秒
		private int expiresIn;
		// 凭证有效期，截止时间
		private Date expiresDate;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public int getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(int expiresIn) {
			this.expiresIn = expiresIn;
		}

		public Date getExpiresDate() {
			return expiresDate;
		}

		public void setExpiresDate(Date expiresDate) {
			this.expiresDate = expiresDate;
		}

		@Override
		public String toString() {
			return "AccessToken [token=" + token + ", expiresIn=" + expiresIn
					+ ", expiresDate=" + expiresDate + "]";
		}
		
}
