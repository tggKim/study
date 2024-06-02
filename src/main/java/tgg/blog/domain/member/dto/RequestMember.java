package tgg.blog.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tgg.blog.domain.member.Member;

@Getter @Setter
public class RequestMember {
    @NotBlank(message = "아이디는 필수 입력 값 입니다")
    private String loginId;

    @NotBlank(message = "사용자 이름을 입력해 주세요")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다")
    private String password;

    public Member toMember(){
        return Member.builder()
                .loginId(this.loginId)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
