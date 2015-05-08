
package com.kronos;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nishant.Awasthi
 *
 */
@RestController
public class OAuthValidator {
	
    private static final String OPENAM_ACCESS_TOKEN_INFO = "http://rhel7-auth3.int.kronos.com:8087/openam/oauth2/tokeninfo";
	 private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/getResource")
    public String getResource(@RequestParam(value="access_token") String accessToken,@RequestParam(value="realm") String realm,@RequestParam(value="resource") String resource) throws URISyntaxException {
//        counter.incrementAndGet()
		 URI url = new URI(OPENAM_ACCESS_TOKEN_INFO);
	     OpenAmOAuthClient client=new OpenAmOAuthClient();
	     OAuthResponse or=client.getTokenInfo(url, accessToken, realm);
        return or!=null?(or.getError()!=null?or.getErrorDescription():or.getScope()[0]):"invalid token";
    }
}
