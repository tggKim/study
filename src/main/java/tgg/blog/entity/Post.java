package tgg.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String title;

    private String content;

    @Column(updatable = false)
    private LocalDateTime  createdDate;

    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Column(columnDefinition = "integer default 0",nullable=false)
    private int view;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    public void update(String title,String content){
        this.title=title;
        this.content=content;
        LocalDateTime now = LocalDateTime.now();
        updatedDate = now;
    }

    public void updateViewCount(){
        view++;
    }
}
