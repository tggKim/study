package tgg.blog.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tgg.blog.domain.post.PostService;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final PostService postService;
    @DeleteMapping("/blog/post/{id}")
    public void delete(@PathVariable("id") Long id){
        System.out.println(id);
        postService.removePost(id);
    }
}
