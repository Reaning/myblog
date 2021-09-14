package life.myblog.controller;

import life.myblog.dto.GiteeDTO;
import life.myblog.dto.UserDTO;
import life.myblog.mapper.UserMapper;
import life.myblog.model.User;
import life.myblog.provider.GiteeProvider;
import life.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;



@Controller
public class AuthorizeController {

    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           HttpServletRequest request,
                           HttpServletResponse response){
        GiteeDTO giteeDTO = new GiteeDTO();
        giteeDTO.setGrant_type("authorization_code");
        giteeDTO.setClient_id(clientId);
        giteeDTO.setClient_secret(clientSecret);
        giteeDTO.setCode(code);
        giteeDTO.setRedirect_uri(redirectUri);
        String accessToken = giteeProvider.getAccessToken(giteeDTO);
        UserDTO userDTO = giteeProvider.getUser(accessToken);
        System.out.println(userDTO.getName());
        if (userDTO.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(userDTO.getName());
            user.setAccountId(String.valueOf(userDTO.getId()));
            user.setAvatarUrl(userDTO.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            // 登录成功，写cookie 和session
            request.getSession().setAttribute("user", userDTO);
            return "redirect:/";
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}
