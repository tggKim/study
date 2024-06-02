package tgg.blog.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.web.dto.RequestUpdatePost;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public Post findById(Long id){
        return postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("값이 null 이다"));
    }

    public List<Post> findAllPost(){
        return postRepository.findAll();
    }

    @Transactional
    public void updatePost(RequestUpdatePost requestUpdatePost){
        Post post = findById(requestUpdatePost.getId());
        post.update(requestUpdatePost.getTitle(),requestUpdatePost.getContent());
    }

    @Transactional
    public void removePost(Long id){
        postRepository.deleteById(id);
    }

    @Transactional
    public void updateViewCount(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("값이 null 이다"));
        post.updateViewCount();
    }
}
