package tgg.blog.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tgg.blog.domain.member.MemberService;
import tgg.blog.domain.member.dto.RequestMember;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/add")
    public String addMemberForm(Model model){
        model.addAttribute("requestMember",new RequestMember());
        return "member/addMember";
    }

    @PostMapping("/members/add")
    public String addMember(@Validated @ModelAttribute("requestMember") RequestMember requestMember, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "member/addMember";
        }
        memberService.saveMember(requestMember.toMember());
        return "redirect:/";
    }
}
