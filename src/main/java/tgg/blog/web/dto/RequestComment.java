package tgg.blog.web.dto;

import lombok.Getter;
import lombok.Setter;
import tgg.blog.domain.comment.Comment;

@Getter @Setter
public class RequestComment {
    private String content;
    public Comment toComment(){
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }
}
