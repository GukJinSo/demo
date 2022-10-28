package gukjin.demo.config.oauth;

import gukjin.demo.entity.User;
import gukjin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc된 loadUserByUsername 함수가 실행됨
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    // 시큐리티 세션에 들어갈 수 있는건 Authentication. Authentication 안에 UserDetails 타입이 들어감
    // 시큐리티 세션(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
