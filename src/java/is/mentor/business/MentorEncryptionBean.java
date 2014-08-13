/**
 *
 */
package is.mentor.business;

import java.util.logging.Logger;

import com.idega.idegaweb.IWMainApplication;
import com.idega.util.StringUtil;
import com.idega.util.encryption.RijndaelEncryptionBean;


/**
 * <p>
 * TODO tryggvil Describe Type MentorEncryptionBean
 * </p>
 *  Last modified: $Date: 2006/03/06 13:04:04 $ by $Author: tryggvil $
 *
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.4 $
 */
public class MentorEncryptionBean extends RijndaelEncryptionBean {

	public static final String BEAN_ID="MentorEncryptionBean";
	public static final String PROPERTY_ENCRYPTION_KEY="is.mentor.encryptionkey";

	private static byte[] initVector = new byte[] {0x49, 0x76, 0x61, 0x6e, 0x20, 0x4d,  0x65, 0x64, 0x76, 0x65, 0x64, 0x65, 0x76, 0x6e, 0x20, 0x4d };

	public static final MentorEncryptionBean getInstance(IWMainApplication iwma){
		MentorEncryptionBean instance = (MentorEncryptionBean) iwma.getAttribute(BEAN_ID);
		if(instance==null){
			instance = new MentorEncryptionBean();
			String key = iwma.getSettings().getProperty(PROPERTY_ENCRYPTION_KEY);
			if(key!=null){
				instance.setKeySize(32);
				instance.setSecretKey(key);
				instance.setIV(initVector);
			}
			iwma.setAttribute(BEAN_ID,instance);
		}
		return instance;
	}

	public static final MentorEncryptionBean getInstance(String key) {
		MentorEncryptionBean instance = new MentorEncryptionBean();
		instance.setKeySize(32);
		instance.setSecretKey(key);
		instance.setIV(initVector);

		return instance;
	}

	public static final MentorEncryptionBean getWebServiceInstance() {
		return getInstance("3ru8w3ryK7373rwr3cr4dt454dvts3vr");
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getEncryptedWebServiceKey
	 * </p>
	 * @return
	 */
	public String getEncryptedWebServiceKey() {
		String key = "cN34n6cSh9AWCd";
		String encrypted = encrypt(key);
		if (StringUtil.isEmpty(encrypted)) {
			Logger.getLogger(getClass().getName()).warning("Failed to encrypt '" + key + "'! Returning not encrypted key");
			return key;
		}
		return encrypted;
	}

}
