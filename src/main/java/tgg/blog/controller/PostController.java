package tgg.blog.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tgg.blog.dto.*;
import tgg.blog.entity.Comment;
import tgg.blog.entity.Post;
import tgg.blog.service.CommentService;
import tgg.blog.service.PostService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

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
    public String post(@PathVariable("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response){
        Cookie oldCookie=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("postView")){
                    oldCookie=cookie;
                }
            }
        }

        if(oldCookie!=null){
            if(!oldCookie.getValue().contains("["+id+"]")){
                postService.updateViewCount(id);
                oldCookie.setValue(oldCookie.getValue()+"_["+id+"]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24);
                response.addCookie(oldCookie);
            }
        }
        else{
            postService.updateViewCount(id);
            Cookie newCookie = new Cookie("postView","["+id+"]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60*60*24);
            response.addCookie(newCookie);
        }

        Post findPost = postService.findById(id);


        ResponsePost post = new ResponsePost(findPost);
        model.addAttribute("post",post);
        model.addAttribute("comments",findPost.getComments());
        return  "post";
    }
    

    @GetMapping("/blog/post")
    public String addPostForm(Model model){
        model.addAttribute("addPost",new Post());
        return "add";
    }

    @PostMapping("/blog/post")
    public String addPost(@Validated @ModelAttribute("addPost") RequestAddPost requestAddPost, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("에러 = {}",bindingResult);
            return "add";
        }

        postService.savePost(requestAddPost.toPost());

        return "redirect:/blog";
    }

    @GetMapping("/blog/update/{id}")
    public String updatePostForm(@PathVariable("id") Long id,Model model){
        Post post = postService.findById(id);
        ResponsePost responsePost = new ResponsePost(post);
        model.addAttribute("updatePost",responsePost);
        return "update";
    }

    @PostMapping("/blog/update/{id}")
    public String updatePost(@Validated @ModelAttribute("updatePost") RequestUpdatePost requestUpdatePost,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("에러 = {}",bindingResult);
            return "update";
        }

        postService.updatePost(requestUpdatePost);

        return "redirect:/blog/post/"+requestUpdatePost.getId();
    }
}
