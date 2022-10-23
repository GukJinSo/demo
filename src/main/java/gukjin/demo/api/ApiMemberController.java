package gukjin.demo.api;

import gukjin.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService errorService;

    @GetMapping("/api/response")
    public ResponseEntity<MemberResponseDto> httpResponse(){
        MemberResponseDto body = new MemberResponseDto(1L, "GJ S");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/api/exception")
    public ResponseEntity<MemberResponseDto> httpResponseWithError(@RequestBody MemberFormDto bodyDto){
        Long savedMemberId = errorService.saveWithException(bodyDto.getId(), bodyDto.getName());
        MemberResponseDto body = new MemberResponseDto(savedMemberId);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
