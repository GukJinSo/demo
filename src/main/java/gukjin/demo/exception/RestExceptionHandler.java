package gukjin.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MemberDuplicateException.class)
    public ResponseEntity<Map> errorServiceExceptionHandler(Exception e) {
        Map<String, String> body = new HashMap<>();
        body.put("Error", "유저 아이디 명 중복");
        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }
}
