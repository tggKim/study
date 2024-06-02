package tgg.blog.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tgg.blog.domain.login.LoginService;
import tgg.blog.domain.member.Member;
import tgg.blog.domain.member.dto.RequestMember;
import tgg.blog.web.login.dto.RequestLogin;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("requestLogin",new RequestLogin());
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute RequestLogin requestLogin, BindingResult bindingResult
                        ,@RequestParam(value = "redirectURL",defaultValue = "/") String redirectURL
                        ,HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginPage";
        }

        Member member = loginService.findMember(requestLogin.getLoginId(), requestLogin.getPassword());
        if(member==null){
            bindingResult.reject("noMember","아이디 또는 비밀번호가 일치하지 않습니다");
            return "login/loginPage";
        }

        HttpSession session = request.getSession();
        session.setAttribute("login",member);
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.removeAttribute("login");
        }
        return "redirect:/";
    }
}
