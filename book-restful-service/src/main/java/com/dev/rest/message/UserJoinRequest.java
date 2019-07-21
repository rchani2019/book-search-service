package com.dev.rest.message;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
	@NotEmpty(message = "ID 또는 비밀번호를 입력하여주세요.")
    @Size(min = 6, max = 30, message = "6자 이상 30자 미만으로 입력하여주세요.")
    private String userId;

    @NotEmpty(message = "ID 또는 비밀번호를 입력하여주세요.")
    private String password;

    @NotEmpty(message = "이름은 2~20자리로 입력해야 합니다.")
    @Size(min = 2, max = 20, message = "이름은 2~20자리로 입력해야 합니다.")
    private String name;
}
