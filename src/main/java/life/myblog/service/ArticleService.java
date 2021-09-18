package life.myblog.service;

import life.myblog.dto.ArticleDTO;
import life.myblog.exception.CustomException;
import life.myblog.exception.CustomExceptionCode;
import life.myblog.mapper.ArticleMapper;
import life.myblog.mapper.UserMapper;
import life.myblog.model.Article;
import life.myblog.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    public ArticleDTO selectById(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null){
            throw new CustomException(CustomExceptionCode.ARTICLE_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(article.getCreator());
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article,articleDTO);
        articleDTO.setUser(user);
        return articleDTO;
    }
}
