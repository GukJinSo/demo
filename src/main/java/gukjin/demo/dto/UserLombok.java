package gukjin.demo.dto;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "builder")
public class UserLombok {

    private Long id;
    private String name;
    private String address;
    private String password;
    private int age;
    private String gender;

}
