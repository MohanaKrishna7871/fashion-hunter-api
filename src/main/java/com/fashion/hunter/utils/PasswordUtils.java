package com.fashion.hunter.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordUtils {
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public String hashPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public boolean checkPassword(String rawPassword, String hashedPassword) {
		return passwordEncoder.matches(rawPassword, hashedPassword);
	}
	
}