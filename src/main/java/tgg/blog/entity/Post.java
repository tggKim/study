package tgg.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime  localDateTime;
}
