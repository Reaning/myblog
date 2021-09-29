package life.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.myblog.dto.UserDTO;
import life.myblog.mapper.VisitorMapper;
import life.myblog.model.Visitor;
import life.myblog.model.VisitorExample;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;


@Controller
public class PassController {
    @Autowired
    private VisitorMapper visitorMapper;

    @GetMapping("/pass.html")
    public String pass(HttpServletRequest request){
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        Visitor dbvisitor = visitorMapper.selectByPrimaryKey(ipAddress);
        getPlace(ipAddress);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(System.currentTimeMillis());
        if(dbvisitor == null){
            Visitor visitor = new Visitor();
            visitor.setIp(ipAddress);
            visitor.setPlace(getPlace(ipAddress));
            visitor.setDatetime(format);
            visitorMapper.insert(visitor);
        }else{
            Visitor visitor = new Visitor();
            visitor.setDatetime(format);
            VisitorExample example = new VisitorExample();
            example.createCriteria()
                            .andIpEqualTo(ipAddress);
            visitorMapper.updateByExampleSelective(visitor, example);
        }
        return "pass";
    }
    public String getPlace(String ipAddress){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://apidatav2.chinaz.com/single/ip?key=052d9995ec51495493bd7f19e9755fc6&ip=" + ipAddress)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            JSONObject responseObject = JSONObject.parseObject(str);
            JSONObject result = JSONObject.parseObject(responseObject.getString("Result"));
            String place = result.getString("Country") + result.getString("Province") + result.getString("City") + " " + result.getString("Isp");
            return place;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
