/**
 * 
 */
package is.mentor.presentation;

import is.mentor.business.MentorEncryptionBean;
import java.io.IOException;
import javax.faces.context.FacesContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Parameter;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.encryption.RijndaelEncryptionBean;


/**
 * <p>
 * Button to post a form to the mentor web site and log the user into
 * that external webapplication.
 * </p>
 *  Last modified: $Date: 2006/01/27 15:49:05 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
 */
public class MentorLoginButton extends PresentationObjectTransitional {

	private String webapplicationUrl = null;
	private String parameterKey1="Lykill1";
	private String parameterKey2="Lykill2";
	private String target="_new";
	public static final String IW_BUNDLE_IDENTIFIER="is.mentor";
	public static final String PROPERTY_LOCAL_IPADDRESS="is.mentor.localipaddress";
	public static final String PROPERTY_SERVER_LOGIN_URL="is.mentor.loginurl";
	
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObject#getBundleIdentifier()
	 */
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	
	/**
	 * @return Returns the parameterKey1.
	 */
	public String getParameterKey1() {
		return parameterKey1;
	}
	
	/**
	 * @param parameterKey1 The parameterKey1 to set.
	 */
	public void setParameterKey1(String parameterKey1) {
		this.parameterKey1 = parameterKey1;
	}
	
	/**
	 * @return Returns the parameterKey2.
	 */
	public String getParameterKey2() {
		return parameterKey2;
	}
	
	/**
	 * @param parameterKey2 The parameterKey2 to set.
	 */
	public void setParameterKey2(String parameterKey2) {
		this.parameterKey2 = parameterKey2;
	}
	
	/**
	 * @return Returns the webapplicationUrl.
	 */
	public String getWebapplicationUrl(IWMainApplication iwma) {
		if(webapplicationUrl==null){
			String sPropLoginUrl = iwma.getSettings().getProperty(PROPERTY_SERVER_LOGIN_URL);
			return sPropLoginUrl;
		}
		else{
			return webapplicationUrl;
		}
	}
	
	/**
	 * @param webapplicationUrl The webapplicationUrl to set.
	 */
	public void setWebapplicationUrl(String webapplicationUrl) {
		this.webapplicationUrl = webapplicationUrl;
	}
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		
		IWContext iwc = IWContext.getIWContext(context);
		IWMainApplication iwma = iwc.getIWMainApplication();
		
		Form form = new Form();
		String webappUrl = getWebapplicationUrl(iwma);
		form.setAction(webappUrl);
		String target = getTarget();
		form.setTarget(target);
		
		Image image = new Image(getImageUri(iwc));
		SubmitButton button = new SubmitButton(image,"Submit");
		form.getChildren().add(button);
		String userPersonalId = getPersonalId(iwc);
		
		String serverIpadress = getLocalServerIPAddress(iwc);
		String encryptedServerIpadress = encryptValue(iwma,serverIpadress);
		Parameter param1 = new Parameter(getParameterKey1(),encryptedServerIpadress);
		form.getChildren().add(param1);
		
		String encryptedPersonalId = encryptValue(iwma,userPersonalId);
		Parameter param2 = new Parameter(getParameterKey2(),encryptedPersonalId);
		form.getChildren().add(param2);
		
		getChildren().add(form);
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getTarget
	 * </p>
	 * @return
	 */
	private String getTarget() {
		return target;
	}
	
	public void setTarget(String target){
		this.target=target;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method encryptValue
	 * </p>
	 * @param userPersonalId
	 * @return
	 */
	private String encryptValue(IWMainApplication iwma, String plaintext) {
		return getEncryptionBean(iwma).encrypt(plaintext);
	}
	
	
	public static RijndaelEncryptionBean getEncryptionBean(IWMainApplication iwma){
		return MentorEncryptionBean.getInstance(iwma);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(arg0);
	}
	
	
	protected String getPersonalId(IWContext iwc){
		//String userPersonalId = "1306635919";
		String userPersonalId = "2411655669";
		if(iwc.isLoggedOn()){
			String personalId = iwc.getCurrentUser().getPersonalID();
			if(personalId!=null && !personalId.equals("")){
				userPersonalId=personalId;
			}
		}
		return userPersonalId;
	}
	
	protected String getLocalServerIPAddress(IWContext iwc){
		
		String propAddr = iwc.getIWMainApplication().getSettings().getProperty(PROPERTY_LOCAL_IPADDRESS);
		if(propAddr!=null){
			return propAddr;
		}
		else{
			String addr = iwc.getRequest().getLocalAddr();
			return addr;
		}
	}
	
	protected String getImageUri(IWContext iwc){
		IWBundle bundle = getBundle(iwc);
		return bundle.getResourcesURL()+"/mentor.gif";
	}
	
	
}
