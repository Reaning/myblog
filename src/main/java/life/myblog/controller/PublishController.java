package life.myblog.controller;


import life.myblog.mapper.ArticleMapper;
import life.myblog.model.Article;
import life.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private ArticleMapper articleMapper;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("image","/images/home-bg.gif");
        model.addAttribute("bigtitle","发布博文");
        model.addAttribute("bigsubtitle","写下你的心得体会");
        return "publish";
    }
    @PostMapping("/publish")
    public String post(@RequestParam(value = "title",required = false) String title,
                       @RequestParam(value = "subtitle",required = false) String subTitle,
                       @RequestParam(value = "description",required = false) String description,
                       @RequestParam(value = "tag",required = false) String tag,
                       @RequestParam(value = "id",required = false) Long id,
                       HttpServletRequest request,
                       Model model){
        User user = (User)request.getSession().getAttribute("user");
        Article article = new Article();
        article.setTitle(title);
        article.setSubTitle(subTitle);
        article.setCreator(user.getId());
        article.setDescription(description);
        article.setTag(tag);
        article.setCommentCount(0);
        article.setLikeCount(0);
        article.setViewCount(0);
        article.setGmtCreate(System.currentTimeMillis());
        article.setGmtModified(System.currentTimeMillis());
        articleMapper.insert(article);
        return "redirect:/";
    }
}
