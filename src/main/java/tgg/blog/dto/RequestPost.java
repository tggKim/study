package tgg.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.blog.entity.Post;

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
