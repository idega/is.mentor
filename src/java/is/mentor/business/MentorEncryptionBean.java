/**
 * 
 */
package is.mentor.business;

import com.idega.idegaweb.IWMainApplication;
import com.idega.util.encryption.RijndaelEncryptionBean;


/**
 * <p>
 * TODO tryggvil Describe Type MentorEncryptionBean
 * </p>
 *  Last modified: $Date: 2006/02/02 16:15:28 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
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
	
}
