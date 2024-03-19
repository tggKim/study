package tgg.blog.dto;

import lombok.Getter;
import lombok.Setter;
import tgg.blog.entity.Post;

import java.time.LocalDateTime;

@Getter @Setter
public class ResponseListPost {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ResponseListPost(Post post) {
        this.id=post.getId();
        this.title = post.getTitle();
        this.createdDate = post.getCreatedDate();
        this.updatedDate = post.getUpdatedDate();
    }
}
