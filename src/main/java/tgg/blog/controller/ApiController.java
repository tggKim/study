package tgg.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tgg.blog.service.PostService;

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
