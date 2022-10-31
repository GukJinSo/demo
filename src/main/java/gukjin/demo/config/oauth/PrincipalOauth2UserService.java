package gukjin.demo.config.oauth;

import gukjin.demo.entity.User;
import gukjin.demo.entity.UserRole;
import gukjin.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후 처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인할지 설정
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

        // 구글 로그인 성공 시 oauth-client 라이브러리가 code를 리턴받음 -> accessToken 요청
        // userRequest 정보로 loadUser() 호출 -> 구글로부터 회원 데이터를 받아옴
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes: " + oAuth2User.getAttributes());
        String requestProvider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Info oAuth2Info = null;
        if(requestProvider.equals("naver")){
            oAuth2Info = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if(requestProvider.equals("google")) {
            oAuth2Info = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        String provider = oAuth2Info.getProvider(); ; // ex)google, naver
        String providerId = oAuth2Info.getProviderId();// 구글, 네이버가 관리하는 pk ex)19239949129
        String username = provider+"_"+providerId; // ex) google_19239949129
        String password = passwordEncoder.encode("패스워드별의미없음");
        String email = oAuth2Info.getEmail();

        // 회원이 등록되어 있지 않으면 회원 가입
        User user = userRepository.findByUsername(username);
        if(user == null){
            user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .role(UserRole.ROLE_USER)
                    .createDate(LocalDateTime.now())
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }

        //이 함수는 OAuth2User를 리턴하지만, 다형성을 활용하여 자식으로 리턴.
        //일반적인 회원가입의 경우 User 정보밖에 없겠지만, OAuth2User는 attributes라는 구글 정보가 담긴 map을 같이 들고 있다.
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
