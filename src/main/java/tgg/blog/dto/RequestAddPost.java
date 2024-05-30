package tgg.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.blog.entity.Post;

@NoArgsConstructor
@Getter @Setter
public class RequestAddPost {

    @NotBlank(message="제목을 입력해주세요")
    private String title;
    @NotBlank(message="내용을 입력해주세요")
    private String content;

    public Post toPost(){
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }
}
