package com.olympicService.olympicAPI.Service.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

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

	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				InetAddress inet = null;

				inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 第一個IP為客戶端真實IP
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public String fakeIp(String ip) {

		String[] ipArray = ip.split("\\.");
		
		StringBuilder newIp = new StringBuilder(ipArray[2].toString());

		if (newIp.length() == 1) {
			if (newIp.toString().equals("2")) {
				newIp.setCharAt(0, '1');
				ipArray[2] = newIp.toString();
			} else {
				newIp.setCharAt(0, '9');
				ipArray[2] = newIp.toString();
			}
		} else if (newIp.length() == 2) {
			if (newIp.toString().substring(1,2).equals("3")) {
				newIp.setCharAt(2, '1');
				ipArray[2] = newIp.toString();
			}else {
				newIp.setCharAt(1, '3');
				ipArray[2] = newIp.toString();
			}
		} else {
			if (newIp.toString().substring(2,3).equals("3")) {
				newIp.setCharAt(2, '1');
				ipArray[2] = newIp.toString();
			}else {
				newIp.setCharAt(2, '3');
				ipArray[2] = newIp.toString();
			}
		}

		return ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + "." + ipArray[3];
	}
}
