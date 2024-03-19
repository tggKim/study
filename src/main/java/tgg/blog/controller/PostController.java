package tgg.blog.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tgg.blog.dto.RequestPost;
import tgg.blog.dto.ResponseListPost;
import tgg.blog.dto.ResponsePost;
import tgg.blog.entity.Post;
import tgg.blog.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //첫 화면 Post의 목록을 출력
    @GetMapping("/blog")
    public String posts(Model model){
        List<ResponseListPost> posts =postService.findAllPost()
                .stream()
                .map(ResponseListPost::new)
                .toList();
        model.addAttribute("posts",posts);
        return  "home";
    }

    //Post에 대한 상세 정보
    @GetMapping("/blog/post/{id}")
    public String post(@PathVariable("id") Long id,Model model){
        Post findPost = postService.findById(id);
        ResponsePost post = new ResponsePost(findPost);
        model.addAttribute("post",post);
        return  "post";
    }

    //파라미터 id의 여부에 따라 있으면 수정페이지, 없으면 생성페이지
    @GetMapping("/blog/post")
    public String posting(@RequestParam(name="id",required = false) Long id,Model model){
        ResponsePost post;
        if(id==null){
            post = new ResponsePost(new Post());
        }
        else{
            Post findPost = postService.findById(id);
            post = new ResponsePost(findPost);
        }
        model.addAttribute("post",post);
        return  "addOrUpdate";
    }

    //수정이면  id null 아니고, 생성이면 id null
    @PostMapping("/blog/post")
    public String addOrUpdate(@ModelAttribute RequestPost requestPost,Model model){
        if(requestPost.getId()==null){
            Post post = requestPost.toPost();
            postService.savePost(post);
            return "redirect:/blog";
        }
        else{
            System.out.println("not null");
            Post post = requestPost.toPost();
            postService.savePost(post);
            ResponsePost responsePost = new ResponsePost(post);
            model.addAttribute("post",responsePost);
            return "redirect:/blog/post/"+post.getId();
        }
    }

    @PostConstruct
    public void method(){
        Post post1 = new Post();
        post1.setTitle("kim1");
        post1.setContent("hello");

        Post post2 = new Post();
        post2.setTitle("kim2");
        post2.setContent("bye");

        postService.savePost(post1);
        postService.savePost(post2);
    }
}
