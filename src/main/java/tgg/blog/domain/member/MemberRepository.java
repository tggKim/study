package tgg.blog.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findMemberByLoginId(String loginId);

//    @Query("SELECT m FROM Member m WHERE m.loginId = :loginId AND m.password = :password")
//    Optional<Member> findMemberByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
}
