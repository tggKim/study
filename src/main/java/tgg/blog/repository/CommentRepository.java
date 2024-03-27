package tgg.blog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgg.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
