package gukjin.demo.config.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2Info{

    private String provider = "naver";
    private String providerId;
    private String name;
    private String email;

    public NaverUserInfo(Map<String, Object> attributes) {
        providerId = (String) attributes.get("id");
        name = (String) attributes.get("name");
        email = (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
