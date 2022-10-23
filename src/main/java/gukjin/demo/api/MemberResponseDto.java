package gukjin.demo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private String name;

    public MemberResponseDto(Long id) {
        this.id = id;
    }
}
