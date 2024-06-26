package tgg.blog.web.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tgg.blog.web.dto.RequestComment;
import tgg.blog.domain.comment.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/blog/post/comment/{postId}")
    public String addComment(@PathVariable("postId") Long id, @ModelAttribute RequestComment requestComment){
        commentService.saveComment(id,requestComment);
        return "redirect:/blog/post/"+id;
    }
}
