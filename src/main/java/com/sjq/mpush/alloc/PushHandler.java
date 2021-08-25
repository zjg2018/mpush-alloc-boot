/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */

package com.sjq.mpush.alloc;

import com.alibaba.fastjson.JSONObject;
import com.mpush.api.push.*;
import com.mpush.tools.Jsons;
import com.mpush.tools.common.Strings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ohun on 16/9/7.
 *
 * @author ohun@live.cn (夜色)
 */
/*package*/ final class PushHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PushSender pushSender = PushSender.create();
    private final AtomicInteger idSeq = new AtomicInteger();

    public void start() {
        pushSender.start();
    }

    public void stop() {
        pushSender.stop();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

    public PushResult sendPush(JSONObject params) {
        PushResult result=null;
        String pushId = params.containsKey("pushId") ? params.getString("pushId") : "";
        String userId = params.containsKey("userId") ? params.getString("userId") : "";
        String title = params.containsKey("title") ? params.getString("title") : "苏警通推送";
        String content = params.containsKey("content") ? params.getString("content") : "";
        Boolean broadcast = params.containsKey("broadcast") ? params.getBoolean("broadcast") : true;
        String condition = params.containsKey("condition") ? params.getString("condition") : "";
        String pkgname = params.containsKey("pkgname") ? params.getString("pkgname") : "";
        String url = params.containsKey("url") ? params.getString("url") : "";
        JSONObject extrasObj = params.containsKey("extras") ? params.getJSONObject("extras") : new JSONObject();

        NotificationDO notificationDO = new NotificationDO();
        notificationDO.pushId = pushId;
        notificationDO.content = content;
        notificationDO.title = title;
        notificationDO.nid = idSeq.get() % 2 + 1;
        notificationDO.ticker = title + " 你有一条新的消息,请注意查收";
        notificationDO.pkgname = pkgname;
        notificationDO.url = url;
        notificationDO.extras = extrasObj;
        PushMsg pushMsg = PushMsg.build(MsgType.NOTIFICATION_AND_MESSAGE, Jsons.toJson(notificationDO));
        pushMsg.setMsgId("msg_" + idSeq.incrementAndGet());
        FutureTask<PushResult> futureTask = pushSender.send(PushContext
                .build(pushMsg)
                .setUserId(Strings.isBlank(userId) ? null : userId)
                .setBroadcast(broadcast != null && broadcast)
                .setCondition(Strings.isBlank(condition) ? null : condition));
        try {
             result = futureTask.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] readBody(HttpExchange httpExchange) throws IOException {
        InputStream in = httpExchange.getRequestBody();
        String length = httpExchange.getRequestHeaders().getFirst("content-length");
        if (length != null && !length.equals("0")) {
            byte[] buffer = new byte[Integer.parseInt(length)];
            in.read(buffer);
            in.close();
            return buffer;
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            return out.toByteArray();
        }
    }

    public static final class NotificationDO {
        public String pushId;
        public String msgId;
        public String title;
        public String content;
        public String pkgname;
        public String url;
        public Integer nid; //主要用于聚合通知，非必填
        public Byte flags; //特性字段。 0x01:声音  0x02:震动  0x03:闪灯
        public String largeIcon; // 大图标
        public String ticker; //和title一样
        public Integer number;
        public JSONObject extras;
    }
}
