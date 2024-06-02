package tgg.blog.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgg.blog.domain.comment.CommentService;
import tgg.blog.domain.post.PostService;

@SpringBootTest
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;
    @Autowired
    EntityManager em;

    @Test
    public void test(){
    }
}