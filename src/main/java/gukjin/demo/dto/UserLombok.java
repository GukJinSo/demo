package gukjin.demo.dto;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "innerBuilder", builderClassName = "Builder")
public class UserLombok {

    private Long id;
    private String name;
    private String address;
    private String password;
    private int age;
    private String gender;

    public static Builder builder(Long id){
        return innerBuilder().id(id);
    }

}
