package life.myblog.dto;

import life.myblog.model.User;
import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String subTitle;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private String description;
    private User user;
}
