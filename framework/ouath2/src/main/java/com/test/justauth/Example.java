package com.test.justauth;

import me.zhyd.oauth.AuthRequestBuilder;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

/**
 * @author simple
 */
public class Example {
    public static final AuthCallback callback = AuthCallback.builder().build();

    public static void auth1() {
        //普通方式
        // 创建授权request
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
            .clientId("clientId")
            .clientSecret("clientSecret")
            .redirectUri("redirectUri")
            .build());
        // 生成授权页面
        authRequest.authorize("state");
        // 授权登录后会返回code（auth_code（仅限支付宝））、state，1.8.0版本后，可以用AuthCallback类作为回调接口的参数
        // 注：JustAuth默认保存state的时效为3分钟，3分钟内未使用则会自动清除过期的state
        authRequest.login(callback);
    }

    public static void auth2() {
        //    Builder 方式一
        //    静态配置 AuthConfig

        AuthRequest authRequest = AuthRequestBuilder.builder()
            .source("github")
            .authConfig(AuthConfig.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .redirectUri("redirectUri")
                .build())
            .build();
        // 生成授权页面
        authRequest.authorize("state");
        // 授权登录后会返回code（auth_code（仅限支付宝））、state，1.8.0版本后，可以用AuthCallback类作为回调接口的参数
        // 注：JustAuth默认保存state的时效为3分钟，3分钟内未使用则会自动清除过期的state
        authRequest.login(callback);
    }

    public static void auth3() {
        //        Builder 方式二
        //        动态获取并配置 AuthConfig

        AuthRequest authRequest = AuthRequestBuilder.builder()
            .source("gitee")
            .authConfig((source) -> {
                // 通过 source 动态获取 AuthConfig
                // 此处可以灵活的从 sql 中取配置也可以从配置文件中取配置
                return AuthConfig.builder()
                    .clientId("clientId")
                    .clientSecret("clientSecret")
                    .redirectUri("redirectUri")
                    .build();
            })
            .build();
//        Assert.assertTrue(authRequest instanceof AuthGiteeRequest);
        System.out.println(authRequest.authorize(AuthStateUtils.createState()));
    }

    public static void auth4() {
        // Builder 方式支持自定义的平台
        AuthRequest authRequest = AuthRequestBuilder.builder()
            // 关键点：将自定义实现的 AuthSource 配置上
            .extendSource(null)
            // source 对应 AuthExtendSource 中的枚举 name
            .source("other")
            // ... 其他内容不变，参考上面的示例
            .build();
    }
}
