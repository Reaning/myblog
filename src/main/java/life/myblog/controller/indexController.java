package life.myblog.controller;

import life.myblog.mapper.ArticleMapper;
import life.myblog.model.Article;
import life.myblog.model.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class indexController {
    @Autowired
    private ArticleMapper articleMapper;
    @GetMapping("/")
    public String index(Model model){
        List<Article> articles = articleMapper.selectByExample(new ArticleExample());
        model.addAttribute("articles",articles);
        model.addAttribute("image","/images/home-bg.jpg");
        return "index";
    }
}
