package Controller;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;


/**
* This was a helper class for socket communication security, however it is not used because we found out socket security was not required.
* 
* @author Florin Ciornei
* 
*/
public class Security {

	public static PublicKey parseKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger exponent = new BigInteger(1, Base64.getDecoder().decode(key.split("\\.")[0]));
		BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(key.split("\\.")[1]));
		RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory factory = KeyFactory.getInstance("RSA/None/PKCS1PADDING");
		return factory.generatePublic(spec);
	}

	public static byte[] encrypt(PublicKey key, String message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
	}
}
