/**
 * 
 */
package hello;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Nishant.Awasthi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthConfiguration {
	
	
	private String clientId="top_oauth";
	private String realm="/";
	private String grantType="password";
	private String username="demo";
	private String password="kronites";
	private String clientSecret="kronites";
	public String getRealm() {
		return realm;
	}


	public void setRealm(String realm) {
		this.realm = realm;
	}


	public String getGrantType() {
		return grantType;
	}


	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getClientSecret() {
		return clientSecret;
	}


	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}



	
	public OAuthConfiguration() {
		
	}
	
	/**
	 * @param realm
	 * @param grantType
	 * @param username
	 * @param password
	 * @param clientSecret
	 * @param clientId
	 */
	public OAuthConfiguration(String realm, String grantType, String username,
			String password, String clientSecret, String clientId) {
		super();
		this.realm = realm;
		this.grantType = grantType;
		this.username = username;
		this.password = password;
		this.clientSecret = clientSecret;
		this.clientId = clientId;
	}
	
	public MultiValueMap<String, String> getHttpEntityBody(){
		MultiValueMap<String, String> map=new LinkedMultiValueMap<String, String>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("realm", realm);
		map.add("username", username);
		map.add("password", password);
		map.add("grant_type", grantType);
		return map;
	}

}
