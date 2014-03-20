package dk.dtu.ds.server.aux;


import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;

class UserContainer {
	
	private String username;
	private byte[] hash;
	private byte[] salt;
	
	public UserContainer(String username, byte[] hash, byte[] salt){
		this.username = username;
		System.arraycopy(hash, 0, this.hash, 0, hash.length);
		System.arraycopy(salt, 0, this.salt, 0, salt.length);
	}
}


public class CryptoWrapper {
	
	private static final int SALT_SIZE 		= 32;
	private static final int ITTERATIONS 	= 1000;
	
	public static byte[] generateRandomSalt(){
		// Instantiates a new secure random
		SecureRandom random = new SecureRandom();
        byte salt[] = new byte[SALT_SIZE];
        // Gets a random - secure - salt for the password
        random.nextBytes(salt);
		return salt;
	}
	
	public static byte[] hashPassword(char[] password, byte[] salt){
		//PBEKeySpec(char[] password, byte[] salt, int iterationCount)
		
		try{
			KeySpec ks = new PBEKeySpec(password, salt, ITTERATIONS, 8*SALT_SIZE);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(ks).getEncoded();
		}catch(Exception E){
			System.err.print("CryptoError: "); E.printStackTrace();
		}
		
		
		return null;
	}
	
	
}
