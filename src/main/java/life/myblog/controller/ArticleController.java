package life.myblog.controller;

import life.myblog.dto.ArticleDTO;
import life.myblog.mapper.ArticleMapper;
import life.myblog.model.Article;
import life.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @GetMapping("/article/{id}")
    public String article(@PathVariable(name = "id")Long id,
                          Model model){
        ArticleDTO articleDTO = articleService.selectById(id);
        model.addAttribute("image","/images/cs.png");
        model.addAttribute("bigtitle",articleDTO.getTitle());
        model.addAttribute("bigsubtitle",articleDTO.getSubTitle());
        model.addAttribute("article",articleDTO);
        return "article";
    }
}
