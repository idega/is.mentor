/**
 * 
 */
package is.mentor.presentation;

import java.io.IOException;
import is.mentor.business.EncryptionBean;
import javax.faces.context.FacesContext;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Parameter;
import com.idega.presentation.ui.SubmitButton;


/**
 * <p>
 * Button to post a form to the mentor web site and log the user into
 * that external webapplication.
 * </p>
 *  Last modified: $Date: 2006/01/25 01:37:10 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class MentorLoginButton extends PresentationObjectTransitional {

	private String webapplicationUrl = "http://217.151.171.250/test/default.aspx?login=remote";
	private String encryptionKey="Kui29Sie46K7seTkei45SfY6J8husr18";
	private String parameterKey1="Lykill1";
	private String parameterKey2="Lykill2";
	public static final String IW_BUNDLE_IDENTIFIER="is.mentor";
	
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObject#getBundleIdentifier()
	 */
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}

	/**
	 * @return Returns the encryptionKey.
	 */
	public String getEncryptionKey() {
		return encryptionKey;
	}
	
	/**
	 * @param encryptionKey The encryptionKey to set.
	 */
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
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
	public String getWebapplicationUrl() {
		return webapplicationUrl;
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
		
		IWBundle bundle = getBundle(iwc);
		
		Form form = new Form();
		String webappUrl = getWebapplicationUrl();
		form.setAction(webappUrl);
		form.setTarget("_new");
		
		
		Image image = bundle.getImage("mentor.gif");
		SubmitButton button = new SubmitButton(image,"mentorlogin");
		form.getChildren().add(button);
		String userPersonalId = "1306635919";
		if(iwc.isLoggedOn()){
			String personalId = iwc.getCurrentUser().getPersonalID();
			if(personalId!=null && !personalId.equals("")){
				userPersonalId=personalId;
			}
		}
		
		String serverIpadress = "157.157.136.146";
		String encryptedServerIpadress = encryptValue(serverIpadress);
		Parameter param1 = new Parameter(getParameterKey1(),encryptedServerIpadress);
		form.getChildren().add(param1);
		
		String encryptedPersonalId = encryptValue(userPersonalId);
		Parameter param2 = new Parameter(getParameterKey2(),encryptedPersonalId);
		form.getChildren().add(param2);
		
		getChildren().add(form);
	}

	/**
	 * <p>
	 * TODO tryggvil describe method encryptValue
	 * </p>
	 * @param userPersonalId
	 * @return
	 */
	private String encryptValue(String userPersonalId) {
		return getEncryptionBean().encrypt(userPersonalId);
	}
	
	
	protected EncryptionBean getEncryptionBean(){
		return EncryptionBean.getInstance();
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
}
