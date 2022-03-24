package com.olympicService.olympicAPI.Service.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class Tool {

	public String getMD5(String password) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] messageDigest = md.digest(password.getBytes());

		BigInteger no = new BigInteger(1, messageDigest);

		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}
}
