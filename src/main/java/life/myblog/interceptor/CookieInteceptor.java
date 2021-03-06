package life.myblog.interceptor;

import life.myblog.mapper.UserMapper;
import life.myblog.model.User;
import life.myblog.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CookieInteceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        StringBuffer requestURL = request.getRequestURL();
        String curUrl = requestURL.toString();
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
//        System.out.println(ipAddress);
//        System.out.println(curUrl);
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length >= 1) {
            List<Cookie> token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token")).collect(Collectors.toList());
            if (token.size() == 1) {
                UserExample example = new UserExample();
                example.createCriteria()
                        .andTokenEqualTo(token.get(0).getValue());
                List<User> users = userMapper.selectByExample(example);
                if (users != null && users.size() > 0) {
                    request.getSession().setAttribute("user", users.get(0));
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

