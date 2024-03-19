package tgg.blog.dto;

import lombok.Getter;
import lombok.Setter;
import tgg.blog.entity.Post;

@Getter @Setter
public class ResponsePost {
    private Long id;
    private String title;
    private String content;
    public ResponsePost(Post post){
        this.id=post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
    }
}
