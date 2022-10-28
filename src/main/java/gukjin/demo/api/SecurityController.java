package gukjin.demo.api;


import gukjin.demo.config.oauth.PrincipalDetails;
import gukjin.demo.entity.User;
import gukjin.demo.entity.UserRole;
import gukjin.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public String loginTest(Authentication authentication){
        System.out.println("/test/login=========================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication: "+principalDetails);
        return "세션 정보 확인하기";
    }

    @GetMapping("/user")
    public ResponseEntity userSecurity(){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody User user){
        System.out.println(user);
        user.setRole(UserRole.ROLE_USER);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        userRepository.save(user); // 패스워드 암호화하지 않으면 로그인 불가능
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity adminSecurity(){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/joinAdmin")
    public ResponseEntity joinAdmin(@RequestBody User user){
        System.out.println(user);
        user.setRole(UserRole.ROLE_ADMIN);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        userRepository.save(user); // 패스워드 암호화하지 않으면 로그인 불가능
        return ResponseEntity.ok(null);
    }





}
