package com.sjq.mpush.alloc;

import com.alibaba.fastjson.JSONObject;
import com.mpush.api.push.PushCallback;
import com.mpush.api.push.PushResult;
import com.mpush.api.service.BaseService;
import com.mpush.api.service.Listener;
import com.mpush.tools.log.Logs;

/**
 * Created by yxx on 2016/5/6.
 *
 * @author ohun@live.cn
 */
public final class AllocServer extends BaseService {

    private AllocHandler allocHandler;
    private PushHandler pushHandler;

    public String getServer() {
        String str="";
        try{
          str= allocHandler.getServer();
        }catch (Exception e){

        }
        return str;
    }

    public PushResult push(JSONObject params) {
         return   pushHandler.sendPush(params);
    }

    @Override
    public void init() {
        this.allocHandler = new AllocHandler();
        this.pushHandler = new PushHandler();
    }

    @Override
    protected void doStart(Listener listener) throws Throwable {
        pushHandler.start();
        allocHandler.start();
        Logs.Console.info("===================================================================");
        Logs.Console.info("====================ALLOC SERVER START SUCCESS=====================");
        Logs.Console.info("===================================================================");
    }

    @Override
    protected void doStop(Listener listener) throws Throwable {
        pushHandler.stop();
        allocHandler.stop();
        Logs.Console.info("===================================================================");
        Logs.Console.info("====================ALLOC SERVER STOPPED SUCCESS=====================");
        Logs.Console.info("===================================================================");
    }
}
