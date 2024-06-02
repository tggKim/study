package tgg.blog.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String name;

    private String password;

    @Builder
    public Member(String loginId,String name,String password){
        this.loginId=loginId;
        this.name=name;
        this.password=password;
    }
}
