package com.sjq.mpush.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpush.api.push.PushCallback;
import com.mpush.api.push.PushResult;
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
        String res = MpushUtil.getAllocServer().getServer();
        res = res.replace("50.88.1.227", "10.101.0.90");
        return res;
    }

    @RequestMapping("/push")
    public Result push(@RequestBody String content) {
        log.info("content----------" + content);
        JSONObject contentObj = null;
        try {
            contentObj = JSONObject.parseObject(content);
        } catch (Exception e) {
            return Result.error("请推送结构化数据");
        }
        PushResult pushResult=MpushUtil.getAllocServer().push(contentObj);
        return Result.ok(pushResult==null?4:pushResult.getResultCode());
    }
}
