package com.docsconsole.tutorials.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);
    private final RestTemplate restTemplate;
    public CustomLogoutHandler(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        customLogout((OidcUser) auth.getPrincipal());
    }
    private void customLogout(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Logout succeeded from Keycloak");
        } else {
            logger.error("Logout not succeeded from Keycloak");
        }
    }
}