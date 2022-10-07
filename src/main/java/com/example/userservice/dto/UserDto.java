package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDate createdAt;

    private String encryptedPwd;
    private List<ResponseOrder> orders;

}
