package com.test.justauth.controller;

import com.alibaba.fastjson.JSON;
import com.test.justauth.entity.GithubAuthedUser;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGithubScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    Map<String, GithubAuthedUser> githubAuthedUserMap = new ConcurrentHashMap<>();

    @RequestMapping("/render/github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        logger.info("/render/github");
        var request = new AuthGithubRequest(authConfig());
        response.sendRedirect(request.authorize(null)); // null 时 会生成uuid当成state的参数， 在authorize时候将 state 保存在 AuthDefaultStateCache
    }

    @RequestMapping("/callback/github")
    public Object login(AuthCallback callback) {
        logger.info("callback => " + JSON.toJSONString(callback));
        AuthRequest authRequest = new AuthGithubRequest(authConfig());
        var response = authRequest.login(callback);
        logger.info("response => " + JSON.toJSONString(response));
        if (response.ok()) {
            var data = JSON.parseObject(JSON.toJSONString(response.getData()), GithubAuthedUser.class);
            githubAuthedUserMap.put(data.uuid, data);
        }
        return response;
    }

    // GitHub Not Implemented revoke method
    @RequestMapping("/revoke/{uuid}")
    public String revokeAuth(@PathVariable("uuid") String uuid) {
        GithubAuthedUser githubAuthedUser = githubAuthedUserMap.get(uuid);
        if (githubAuthedUser == null) return "not found";
        AuthRequest authRequest = new AuthGithubRequest(authConfig());
        var response = authRequest.revoke(convert(githubAuthedUser.token));
        logger.info("revoke response => " + JSON.toJSON(response));
        githubAuthedUserMap.remove(uuid);
        return "OK";
    }

    // GitHub Not Implemented refresh method
    @RequestMapping("/refresh/github/{uuid}")
    public String refreshAuth(@PathVariable("uuid") String uuid) {
        AuthRequest authRequest = new AuthGithubRequest(authConfig());

        var user = githubAuthedUserMap.get(uuid);
        if (null == user) {
            return "用户不存在";
        }
        AuthResponse<AuthToken> response = authRequest.refresh(convert(user.token));
        if (response.ok()) {
            user.getToken().accessToken = response.getData().getAccessToken();
            return "OK.";
        }
        return "failed.";
    }

    private AuthToken convert(GithubAuthedUser.Token token) {
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(token.accessToken);
        authToken.setTokenType(token.tokenType);
        return authToken;
    }

    private AuthConfig authConfig() {
        return AuthConfig
            .builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUri("https://e198-58-23-240-199.ngrok.io/oauth/callback/github")
            .scopes(AuthScopeUtils.getScopes(AuthGithubScope.values()))
            .build();
    }
}
