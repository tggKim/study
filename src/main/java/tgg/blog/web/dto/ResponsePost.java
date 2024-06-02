package tgg.blog.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgg.blog.domain.post.Post;


// "/blog/post/{id}" 응답에 사용하는 객체 id,title,content에 대한 정보 가지는 dto
//  "/blog/post" 응답에 사용 파라미터 id가 있으면 해당 Post 객체의 내용 담기고, 없으면 null 값이 담긴 객체로 넘겨짐
@Getter @Setter
@NoArgsConstructor
public class ResponsePost {
    private Long id;
    private String title;
    private String content;
    public ResponsePost(Post post){
        this.id=post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
    }
}
