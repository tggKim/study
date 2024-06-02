package tgg.blog.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgg.blog.domain.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
