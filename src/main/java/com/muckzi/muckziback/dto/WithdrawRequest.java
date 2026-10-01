package com.muckzi.muckziback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

}
