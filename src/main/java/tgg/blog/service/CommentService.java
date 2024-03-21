package tgg.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.dto.RequestComment;
import tgg.blog.entity.Comment;
import tgg.blog.entity.Post;
import tgg.blog.repository.CommentRepository;
import tgg.blog.repository.PostRepository;

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
