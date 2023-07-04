package com.neu.esd.clique.model;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Component;

//@Component
public class JwtKeys {
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public JwtKeys(PublicKey publicKey, PrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}
}

