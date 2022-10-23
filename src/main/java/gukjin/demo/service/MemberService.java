package gukjin.demo.service;

import gukjin.demo.exception.MemberDuplicateException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public Long saveWithException(Long id, String name){
        if(true){
            throw new MemberDuplicateException();
        }
        return null;
    }

}
