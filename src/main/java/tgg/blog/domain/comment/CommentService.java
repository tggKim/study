package tgg.blog.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.domain.post.PostService;
import tgg.blog.web.dto.RequestComment;
import tgg.blog.domain.post.Post;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    @Transactional
    public Comment saveComment(Long id, RequestComment requestComment){
        Post post = postService.findById(id);
        Comment comment = requestComment.toComment();
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
