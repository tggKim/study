package tgg.blog.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tgg.blog.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public Post save(Post post){
        em.persist(post);
        return post;
    }

    public Optional<Post> findOne(Long id){
        Post post = em.find(Post.class,id);
        return Optional.ofNullable(post);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p",Post.class).getResultList();
    }

    public void remove(Long id){
        em.remove(em.find(Post.class,id));
    }
}
