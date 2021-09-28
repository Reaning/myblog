package life.myblog.controller;

import life.myblog.dto.ArticleDTO;
import life.myblog.mapper.ArticleMapper;
import life.myblog.mapper.UserMapper;
import life.myblog.model.Article;
import life.myblog.model.ArticleExample;
import life.myblog.model.User;
import life.myblog.model.UserExample;
import life.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
public class ApiController {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;


//    @CrossOrigin(value = "http://localhost:8080", maxAge = 1800, allowedHeaders ="*")
    @GetMapping("/getuser")
    public User getUser(@RequestParam("id") Long id){
        User user = usermapper.selectByPrimaryKey(id);
        return user;
    }

    @GetMapping("/getarticle")
    public Article getArticle(@RequestParam("id") Long id){
        System.out.println("123");
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    @PostMapping("/addarticle")
    public Map<String,Object> addArticle(@RequestBody Article article){
        articleMapper.insert(article);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",new String("ok"));
        map.put("message",new String("添加成功"));
        return map;
    }

    @GetMapping("/getalluser")
    public List<User> getAllUser(){
        UserExample example = new UserExample();
        example.createCriteria();
        List<User> users = usermapper.selectByExample(example);
        return users;
    }

    @GetMapping("/getallarticle")
    public List<Article> getAllArticle(){
        ArticleExample example = new ArticleExample();
        example.createCriteria();
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    @GetMapping("/getarticledto")
    public ArticleDTO getArticleDTO(@RequestParam("id")Long id){
        ArticleDTO articleDTO = articleService.selectById(id);
        return articleDTO;
    }
}
