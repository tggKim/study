package tgg.blog.web.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestLogin {
    @NotBlank(message = "아이디는 필수 입력 값 입니다")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다")
    private String password;
}
