/**
 * 
 */
package com.kronos;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nishant.Awasthi
 *
 */
public class OpenAmOAuthClient {

	public OAuthResponse getTokenInfo(URI url, String accessToken,String realm){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("access_token", accessToken);
		map.add("realm", realm);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(map).build();
		System.out.println(uriComponents.toUri());
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());  
        restTemplate.setMessageConverters(messageConverters);
        OAuthResponse ti=null;
        ResponseEntity<OAuthResponse> re=null;
        try{
        	ti= restTemplate.getForObject(uriComponents.toUri(), OAuthResponse.class);
//        	ti=re.getBody();
        }catch(RestClientException rc){
        	if(rc.getMostSpecificCause() instanceof HttpClientErrorException){
        		HttpClientErrorException hcee=(HttpClientErrorException) rc.getMostSpecificCause();
        		System.out.println(hcee.getResponseBodyAsString());
        		try {
					ti = new ObjectMapper().readValue(hcee.getResponseBodyAsString(), OAuthResponse.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
		return ti;
	}

	private HttpEntity<MultiValueMap<String, String>> setHeadersAndConvertors(
			RestTemplate restTemplate, MultiValueMap<String, String> map,
			HttpHeaders headers) {
		HttpEntity<MultiValueMap<String, String>> request= new HttpEntity<MultiValueMap<String,String>>(map, headers);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());  
        restTemplate.setMessageConverters(messageConverters);
		return request;
	}
	
	public OAuthResponse getOAuthResponseObject(OAuthConfiguration oAuthConfiguration,URI url ) {
		RestTemplate restTemplate = new RestTemplate();
        
        
        MultiValueMap<String,String> map=oAuthConfiguration.getHttpEntityBody();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = setHeadersAndConvertors(
				restTemplate, map, headers);
        
        OAuthResponse or= restTemplate.postForObject(url, request , OAuthResponse.class);
        return or;
	}
	
}
