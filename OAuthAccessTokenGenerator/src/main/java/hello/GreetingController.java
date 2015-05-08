package hello;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@PropertySource("classpath:config.properties")
public class GreetingController {
	
	
	
	
	private static final String WITH_TOKEN = "withToken";
	@Value("${resource_owner_url}")
	private String resourceOwnerUrl;
    private static final String OPENAM_ACCESS_TOKEN_URL = "http://rhel7-auth3.int.kronos.com:8087/openam/oauth2/access_token";

	@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    
    @RequestMapping("/getresult")
    public String getResult(HttpSession session,@RequestParam(value="oauthResource", required=true, defaultValue="withoutToken") String oauthResource, Model model) throws URISyntaxException {
        model.addAttribute("oauthResource", oauthResource);
        //get OAuth token
        OAuthResponse authResponse=null;
        long currentTime=Calendar.getInstance().getTime().getTime();
        authResponse=(OAuthResponse) session.getAttribute("authResponse");
        boolean istokenExpired=isTokenExpired(currentTime,authResponse);
        OAuthConfiguration config= new OAuthConfiguration();
        if(istokenExpired){
        	authResponse=new OAuthResponse();
        	URI url = new URI(OPENAM_ACCESS_TOKEN_URL);
        	if(WITH_TOKEN.equals(oauthResource)){
        		authResponse = getOAuthResponseObject(config,url);
        		authResponse.setExpireTimeStamp(currentTime+ new Long(authResponse.getExpiresIn())-1000l);
        		model.addAttribute("authResponse", authResponse);
        		session.setAttribute("authResponse", authResponse);
        	}
        }
        //get Response based on token
        String str = getWebResponse( config, authResponse);
        // populate object to show
        model.addAttribute("responseObj", str);
        
        return "getresult";
    }


	private boolean isTokenExpired(long currentTime, OAuthResponse authResponse) {
		
		if(authResponse != null && authResponse.getExpireTimeStamp() !=null)
			return currentTime > authResponse.getExpireTimeStamp();
		return true;
		
	}


	private String getWebResponse(OAuthConfiguration config,
			OAuthResponse authResponse) {
		RestTemplate restTemplate = new RestTemplate();
        String restWebUrl=resourceOwnerUrl+"getResource";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("access_token", authResponse.getAccessToken());
		map.add("realm", config.getRealm());
		map.add("resource", "test");
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(restWebUrl.toString()).queryParams(map).build();
		System.out.println(uriComponents.toUri());
        ResponseEntity<String> re=restTemplate.getForEntity(uriComponents.toUri(), String.class);
        System.out.println(re.getBody());
		return re.getBody();
	}

	protected OAuthResponse getOAuthResponseObject(OAuthConfiguration oAuthConfiguration,URI url ) {
		
		return (new OpenAmOAuthClient()).getOAuthResponseObject(oAuthConfiguration, url);
	}
	
	@RequestMapping("/oauthform")
	public String oauthform() {
		return "oauthform";
	}

}
