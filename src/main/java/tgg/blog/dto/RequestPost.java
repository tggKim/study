package tgg.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.blog.entity.Post;

// "/blog/post"의 post 요청에 사용 해당 수정이면 id값이 null이 아니고, 생성이면 id값이 null임
@Getter @Setter
@NoArgsConstructor
public class RequestPost {
    private Long id;
    private String title;
    private String content;
    public Post toPost(){
        Post post = new Post();
        post.setId(this.id);
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }
}
