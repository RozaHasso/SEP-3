package Controller;

import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import Model.Measurement;
import Model.Patient;
import sun.awt.CharsetString;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import com.google.gson.Gson;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import javax.crypto.Cipher;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.math.BigInteger;

import com.google.gson.Gson;
import Model.*;


/**
* Client class is responsible for sending measurements to the business tier by using a socket connection
* 
* @author Florin Ciornei
* 
*/

public class Client {

	private Socket socket;

	private DataOutputStream out;
	private DataInputStream in;
	PublicKey key;
	Gson gson;

	public void connect(String ip, int port) {

		try {
			socket = new Socket(ip, port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		gson = new Gson();

		
		
		
		
		//-----------PROTOCOL START--------------
		
		//Ask the server to check if there is a patient with this id.
		System.out.println("Client> " + "is that patient " + Controller.patientId + " still there? ");
		try {
			out.writeUTF("Check patient " + Controller.patientId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//the reply will consist of a byte array that will contain either 1 - patient exists, or 0 - patient doesn't exist.
		byte[] reply = new byte[1];
		try {
			int count = in.read(reply);
			reply = Arrays.copyOf(reply, count);
			//if there is not such a patient device shuts down to avoid flooding the server with measurements.
			if(reply[0]==0) {
				System.out.println("We could not find such a patient. Shutting down.");
				disconnet();
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//------------ PROTOCOL END ---------------
		
		

		
		
		// Artifacts from the socket RSA encryption
		
		// after connection, the server will send us a key.
		// this is the protocol
		// String receivedKey = "";
		// byte[] key1 = new byte[10000];
		// try {
		// int count = in.read(key1);
		// key1=Arrays.copyOf(key1, count);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// receivedKey = new String(key1,StandardCharsets.UTF_8);
		//
		// // converting the key to a java private key
		// try {
		// key = Security.parseKey(receivedKey);
		// } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
		// e.printStackTrace();
		// }

		// byte[] encrypted = null;
		// try {
		// encrypted = Security.encrypt(key, "asd");
		// System.out.println(encrypted.length);
		// for(byte b :encrypted)
		// System.out.print(b+" ");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// try {
		// out.write(encrypted);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Measurement blood = new BloodPressure(1, 2);
		// try {
		// send(blood);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	
	/**
	 * Sends a measurements to the business tier.
	 * @param measurement - the measurements that will be sent to the server.
	 * @throws Exception
	 */
	public void send(Measurement measurement) throws Exception {
		
		// Artifacts from socket RSA encryption
		
		// byte[] encrypted = null;
		// encrypted = Security.encrypt(key, gson.toJson(measurement));
		// out.write(encrypted);

		
		 out.write(gson.toJson(measurement).getBytes(StandardCharsets.UTF_8));

	}

	
	/**
	 * Closing the connection.
	 */
	public void disconnet() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
