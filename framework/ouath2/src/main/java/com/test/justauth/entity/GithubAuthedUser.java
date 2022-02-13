package com.test.justauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@Data
public class GithubAuthedUser {
    public String uuid;
    public String username;
    public String nickname;
    public String avatar;
    public String blog;
    public String company;
    public String location;
    public String email;
    public String remark;
    public String gender;
    public String source;
    public Token token;
    public RawUserInfo rawUserInfo;

    public static class Token {
        public String accessToken;
        public String expireIn;
        public String refreshToken;
        public String refreshTokenExpireIn;
        public String uid;
        public String openId;
        public String accessCode;
        public String unionId;
        public String scope;
        public String tokenType;
        public String idToken;
        public String macAlgorithm;
        public String macKey;
        public String code;
        public String oauthToken;
        public String oauthTokenSecret;
        public String userId;
        public String screenName;
        public String oauthCallbackConfirmed;
    }

    public static class RawUserInfo {
        public String gistsUrl;
        public String reposUrl;
        public String followingUrl;
        public String twitterUsername;
        public String bio;
        public ZonedDateTime createdAt;
        public String login;
        public String type;
        public String blog;
        public String subscriptionsUrl;
        public ZonedDateTime updatedAt;
        public String siteAdmin;
        public String company;
        public String id;
        public String publicRepos;
        public String gravatarId;
        public String email;
        public String organizationsUrl;
        public String hireable;
        public String starredUrl;
        public String followersUrl;
        public String publicGists;
        public String url;
        public String receivedEventsUrl;
        public String followers;
        public String avatarUrl;
        public String eventsUrl;
        public String htmlUrl;
        public String following;
        public String name;
        public String location;
        public String nodeId;
    }
}
