package com.sjq.mpush.controller;

import com.mpush.tools.Jsons;
import com.sjq.mpush.util.MpushUtil;
import com.sjq.mpush.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Auther: zjg
 * @Date: 2021/08/18/11:32
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/")
public class AllocController {

    @PostConstruct
    public void init() {

    }
    @RequestMapping("/")
    public String getServer() {
        return MpushUtil.getAllocServer().getServer();
    }

    @RequestMapping("/push")
    public Result push(@RequestBody String content) {
        log.info("content----------"+content);
        Map<String, Object> params = Jsons.fromJson(content, Map.class);
        MpushUtil.getAllocServer().push(params);
        return Result.ok();
    }
}
