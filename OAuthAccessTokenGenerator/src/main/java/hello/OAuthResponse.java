/**
 * 
 */
package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nishant.Awasthi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthResponse {
	
	private String errorDescription;
	private String error;
	private String accessToken;
	private String refreshToken;
	private String scope;
	private String tokenType;
	private String expiresIn;
	private Long expireTimeStamp;
	
	public Long getExpireTimeStamp() {
		return expireTimeStamp;
	}

	public void setExpireTimeStamp(Long expireTimeStamp) {
		this.expireTimeStamp = expireTimeStamp;
	}

	private String grantType;
	
	public String getGrantType() {
		return grantType;
	}

	@JsonProperty("grant_type")
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}


	@JsonProperty("error_description")
	public String getErrorDescription() {
		return errorDescription;
	}


	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@JsonProperty("error")
	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("refresh_token")
	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@JsonProperty("scope")
	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonProperty("token_type")
	public String getTokenType() {
		return tokenType;
	}


	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@JsonProperty("expires_in")
	public String getExpiresIn() {
		return expiresIn;
	}


	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}


	/**
	 * 
	 */
	public OAuthResponse() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "OAuthResponse [errorDescription=" + errorDescription
				+ ", error=" + error + ", accessToken=" + accessToken
				+ ", refreshToken=" + refreshToken + ", scope=" + scope
				+ ", tokenType=" + tokenType + ", expiresIn=" + expiresIn
				+ ", grantType=" + grantType + "]";
	}

}
