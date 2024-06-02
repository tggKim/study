package tgg.blog.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.domain.post.Post;
import tgg.blog.domain.post.PostService;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;

    @Test
    public void save(){
        Post post = new Post();
        postService.savePost(post);

        Post findPost = postService.findById(post.getId());

        Assertions.assertEquals(post.getId(),findPost.getId());
    }

    @Test
    public void update(){
        Post post = new Post();
        post.setTitle("tgg");
        postService.savePost(post);

        Post updatePost = new Post();
        updatePost.setId(post.getId());
        updatePost.setTitle("tgg2");
        postService.savePost(updatePost);

        Assertions.assertEquals(post.getTitle(),"tgg2");
    }

    @Test
    public void delete(){
        Post post = new Post();
        postService.savePost(post);
        postService.removePost(post.getId());
        try{
            postService.findById(post.getId());
        }
        catch(Exception e){
            return;
        }
        Assertions.fail();
    }
}