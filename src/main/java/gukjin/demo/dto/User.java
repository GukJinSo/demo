package gukjin.demo.dto;

import gukjin.demo.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    private Long id;
    private String name;
    private String address;
    private String password;
    private int age;
    private String gender;

    private User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.password = builder.password;
        this.age = builder.age;
        this.gender = builder.gender;
    }

    public static Builder builder(Long id){
        return new Builder(id);
    }

    public static class Builder{

        private Long id;
        private String name;
        private String address;
        private String password;
        private int age;
        private String gender;

        protected Builder(Long id){
            this.id = id;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder address(String address){
            this.address = address;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }




}
