/**
 * 
 */
package is.mentor.business;

import is.mentor.rijndael.Rijndael_Algorithm;
import java.io.IOException;
import java.security.InvalidKeyException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * <p>
 * TODO tryggvil Describe Type EncryptionBean
 * </p>
 *  Last modified: $Date: 2006/01/25 01:37:10 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class EncryptionBean {
	
	private static EncryptionBean instance;
	private String secretKey="Kui29Sie46K7seTkei45SfY6J8husr18";
	
	private EncryptionBean(){
	}
	
	public static EncryptionBean getInstance(){
		if(instance==null){
			instance=new EncryptionBean();
		}
		return instance;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method encrypt
	 * </p>
	 * @param userPersonalId
	 * @return
	 */
	public String encrypt(String sInputPlaintext) {
		Rijndael_Algorithm algoritm = new Rijndael_Algorithm();

		
		BASE64Decoder base64Decoder = new BASE64Decoder();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		byte[] bInputPlaintext=null;
		bInputPlaintext = sInputPlaintext.getBytes();
		byte[] key=null;
		try {
			key = base64Decoder.decodeBuffer(secretKey);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int keyLengthBytes = key.length;
		int keyLengthBits=keyLengthBytes*8;
		Object sessionKey = null;
		int blockSizeBytes = 16;
		if(bInputPlaintext.length<16){
			blockSizeBytes=bInputPlaintext.length;
		}
		//int rounds = algoritm.getRounds(keyLengthBytes,blockSize);
		try {
			sessionKey = algoritm.makeKey(key,blockSizeBytes);
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		String sInitializationVector = "testvector123456";
		byte[] bInitializationVector=null;
		bInitializationVector = sInitializationVector.getBytes();
		
		//Concatenate the IV and the bInputPlaintext.
		int length = bInputPlaintext.length + bInitializationVector.length;
		byte[] bInputEncryptionText = new byte[length];
		System.arraycopy(bInitializationVector, 0, bInputEncryptionText, 0, bInitializationVector.length);
		System.arraycopy(bInputPlaintext, 0, bInputEncryptionText, bInitializationVector.length, bInputPlaintext.length);
		
		int offset = bInitializationVector.length;
		byte[] encrypted = algoritm.blockEncrypt(bInputEncryptionText,offset,sessionKey,blockSizeBytes);
		
		//String returnString = base64Encoder.encode(encrypted);
		String returnString = new String(encrypted);
		return returnString;
	}
	
	public static void main(String[] args){
		Rijndael_Algorithm.self_test();
		//Rijndael_Properties.list(System.out);
		
		EncryptionBean bean = getInstance();
		String teststring = "1011783159000000";
		String encoded = bean.encrypt(teststring);
		System.out.println("inputString: '"+teststring+"' encrypts to: '"+encoded+"'");
	}
}
