package gukjin.demo.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터에 대한 후 처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인할지 설정
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        // 구글 로그인 성공 시 oauth-client 라이브러리가 code를 리턴받음 -> accessToken 요청
        // userRequest 정보로 loadUser() 호출 -> 구글로부터 회원 데이터를 받아옴

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return oAuth2User;
    }
}
