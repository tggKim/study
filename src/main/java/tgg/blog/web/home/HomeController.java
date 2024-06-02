package tgg.blog.web.home;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgg.blog.domain.member.Member;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if(session==null){
            return "home/firstPage";
        }

        Member member = (Member)session.getAttribute("login");
        if(member==null){
            return "home/firstPage";
        }

        model.addAttribute("member",member);
        return "home/loginedPage";
    }

}
