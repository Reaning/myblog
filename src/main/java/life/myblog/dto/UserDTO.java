package life.myblog.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
