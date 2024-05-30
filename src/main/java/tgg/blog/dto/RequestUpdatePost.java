package tgg.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RequestUpdatePost {
    @NotNull(message="id가 null이면 안됩니다")
    private Long id;
    @NotBlank(message="제목을 입력해주세요")
    private String title;
    @NotBlank(message="내용을 입력해주세요")
    private String content;
}
