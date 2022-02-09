package com.test.justauth.controller;

import com.alibaba.fastjson.JSON;
import me.zhyd.oauth.AuthRequestBuilder;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author simple
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Value("${github.app.client-id}")
    String clientId;
    @Value("${github.app.client-secret}")
    String clientSecret;

    @RequestMapping("/render/github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        logger.info("/render/github");
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize("state"));
    }

    @RequestMapping("/callback/github")
    public Object login(String code) {
        logger.info("code => " + code);
        AuthRequest authRequest = getAuthRequest();
        var response = authRequest.login(AuthCallback.builder().code(code).build());
        logger.info("response => " + JSON.toJSONString(response));
        return response;
    }

    private AuthRequest getAuthRequest() {
        return AuthRequestBuilder.builder()
            .source("github")
            .authConfig(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri("http://127.0.0.1/callback/github")
                .build())
            .build();
    }
}
