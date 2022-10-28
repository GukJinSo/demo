package gukjin.demo.api;

import gukjin.demo.dto.User;
import gukjin.demo.dto.UserLombok;
import gukjin.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService memberService;

    /**
     * 기본 응답 테스트
     */
    @GetMapping("/api/response")
    public ResponseEntity<MemberResponseDto> httpResponse(){
        MemberResponseDto body = new MemberResponseDto(1L, "GJ S");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    /**
     * memberService.saveWithException()에서
     * MemberDuplicateException 발생하여 RestExceptionHandler 타는지 테스트
     */
    @PostMapping("/api/exception")
    public ResponseEntity<MemberResponseDto> httpResponseWithError(@RequestBody MemberFormDto bodyDto){
        Long savedMemberId = memberService.saveWithException(bodyDto.getId(), bodyDto.getName());
        MemberResponseDto body = new MemberResponseDto(savedMemberId);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    /**
     * 301 Moved Permanently Redirect 타는 지 테스트
     */
    @GetMapping("/api/redirectStart")
    public ResponseEntity<MemberResponseDto> httpResponseWithRedirect(){
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/api/redirectEnd"));
        return new ResponseEntity<>(header, HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * GET /api/redirectStart에서 redriect 처리되는 지 테스트
     */
    @GetMapping("/api/redirectEnd")
    public ResponseEntity<MemberResponseDto> httpResponseRedirect(){
        MemberResponseDto body = new MemberResponseDto(10L, "redirected");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    /**
     * cache-control max age를 포함한 테스트
     */
    @GetMapping("/api/cacheControl")
    public ResponseEntity<List<MemberResponseDto>> httpResponseCacheControl(){
        List<MemberResponseDto> list = new ArrayList<>();
        for (int i = 0; i<10000; i++){
            list.add(new MemberResponseDto(Long.valueOf(i), "cache-control"));
        }
        return ResponseEntity.ok().
                cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(list);
    }

    /**
     * Builder 패턴 직접 구현, lombok 이용 구현
     */
    @GetMapping("/api/builder")
    //@CrossOrigin(originPatterns = "*")
    public ResponseEntity<List> httpResponseBuildPattern(){
        User user = User.builder(1L)
                .address("율하동")
                .age(20)
                .name("GJ S")
                .password("askdlaskdEncoded")
                .gender("MALE").build();
        UserLombok userLombok = UserLombok.builder(2L).age(35).name("GJ K").build();
        return ResponseEntity.ok().body(Arrays.asList(user, userLombok));
    }

    /**CORS간 쿠키 전송 확인
     *
     */
    @GetMapping("/api/cookie")
    public ResponseEntity<UserLombok> cookie(HttpServletResponse response){

        UserLombok userLombok = UserLombok.builder(2L).age(35).name("GJ K").build();

            ResponseCookie responseCookie = ResponseCookie.from("name", "gukjin")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .maxAge(20)
                    .build();
            response.addHeader("Set-Cookie", responseCookie.toString());

        return ResponseEntity.ok().body(userLombok);
    }

}
