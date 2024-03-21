package tgg.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    private String content;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void preUpdate(){
        createdDate=LocalDateTime.now();
    }

    public void setPost(Post post){
        post.getComments().add(this);
        this.post=post;
    }
}
