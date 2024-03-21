package tgg.blog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tgg.blog.entity.Comment;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public Comment save(Comment comment){
        em.persist(comment);
        return comment;
    }
}
