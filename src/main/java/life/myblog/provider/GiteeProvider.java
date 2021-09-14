package life.myblog.provider;

import com.alibaba.fastjson.JSON;
import life.myblog.dto.GiteeAccessTokenDTO;
import life.myblog.dto.GiteeDTO;
import life.myblog.dto.UserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken(GiteeDTO giteeDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(giteeDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")//调用的地址
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            GiteeAccessTokenDTO giteeAccessTokenDTO = JSON.parseObject(str, GiteeAccessTokenDTO.class);
            String token = giteeAccessTokenDTO.getAccess_token();
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public UserDTO getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            UserDTO userDTO = JSON.parseObject(str, UserDTO.class);//把string转换成类格式
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
