package tgg.blog.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgg.blog.domain.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
