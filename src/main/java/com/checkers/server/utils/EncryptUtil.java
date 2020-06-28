package com.checkers.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Tool for encryption user passwords
 */
public class EncryptUtil {

	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		String salts = "blabla_salt";
		byte salt[] = salts.getBytes();

		//Add password bytes to digest
		md.update(salt);

		//Get the hash's bytes
		byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		//This bytes[] has bytes in decimal format;
		//Convert it to hexadecimal format
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
