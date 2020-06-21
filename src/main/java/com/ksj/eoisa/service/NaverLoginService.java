package com.ksj.eoisa.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class NaverLoginService {
	
	private final static String CLIENT_ID = "";
	private final static String CLIENT_SECRET = "";
	private final static String REDIRECT_URI = "https://eoisa.ml/sign/oauth/naver/signin";
	private final static String SESSION_STATE = "oauth_state";
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";

	public String generateState() {
		SecureRandom random = new SecureRandom();
		
		return new BigInteger(130, random).toString(32);
	}

	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
	}

	private String getSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_STATE);
	}

	public String getAuthorizationUrl(HttpSession session) {
		String state = generateState();
		setSession(session, state);

        OAuth20Service oauthService = new ServiceBuilder()
                                    .apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
                                    .callback(REDIRECT_URI).state(state)
                                    .build(NaverLoginApiService.instance());

		return oauthService.getAuthorizationUrl();
	}

	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
		String sessionState = getSession(session);
        
        if(StringUtils.equals(sessionState, state)) {
            OAuth20Service oauthService = new ServiceBuilder()
                                        .apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
                                        .callback(REDIRECT_URI).state(state)
                                        .build(NaverLoginApiService.instance());

			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			
			return accessToken;
		}
		
		return null;
	}

	public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
        OAuth20Service oauthService = new ServiceBuilder()
                                    .apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
                                    .callback(REDIRECT_URI)
                                    .build(NaverLoginApiService.instance());

		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();
		
		return response.getBody();
	}
	
}