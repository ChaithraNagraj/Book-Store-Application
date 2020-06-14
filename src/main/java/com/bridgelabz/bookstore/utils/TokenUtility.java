package com.bridgelabz.bookstore.utils;

import com.bridgelabz.bookstore.constants.Constant;

public class TokenUtility {
	private TokenUtility() {
	}

	public static String verifyResponse(Long userId) {
		return Constant.VERIFY_ADDRESS + JwtValidate.createJWT(userId, Constant.REGISTER_EXP);
	}

}
