package tgg.blog.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.entity.Post;
import tgg.blog.repository.PostRepository;

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
        return postRepository.findOne(id).orElseThrow(()->new IllegalArgumentException("값이 null 이다"));
    }

    public List<Post> findAllPost(){
        return postRepository.findAll();
    }

    @Transactional
    public void removePost(Long id){
        postRepository.remove(id);
    }
}
