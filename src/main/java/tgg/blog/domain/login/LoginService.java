package tgg.blog.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tgg.blog.domain.member.Member;
import tgg.blog.domain.member.MemberRepository;
import tgg.blog.domain.member.MemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    public Member findMember(String loginId,String password){
        return memberRepository.findMemberByLoginId(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }
}
