package tgg.blog.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tgg.blog.dto.RequestComment;
import tgg.blog.dto.RequestPost;
import tgg.blog.dto.ResponseListPost;
import tgg.blog.dto.ResponsePost;
import tgg.blog.entity.Comment;
import tgg.blog.entity.Post;
import tgg.blog.service.CommentService;
import tgg.blog.service.PostService;

import java.util.List;

@Controller
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
            postService.savePost(requestPost.toPost());
            return "redirect:/blog";
        }
        else{
            postService.updatePost(requestPost);
            ResponsePost post = new ResponsePost(postService.findById(requestPost.getId()));
            model.addAttribute("post",post);
            return "redirect:/blog/post/"+post.getId();
        }
    }

    @PostMapping("/blog/post/comment/{postId}")
    public String addComment(@PathVariable("postId") Long id, @ModelAttribute RequestComment requestComment){
        commentService.saveComment(id,requestComment);
        return "redirect:/blog/post/"+id;
    }

}
