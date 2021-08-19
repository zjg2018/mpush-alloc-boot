package com.sjq.mpush.config;

import com.sjq.mpush.alloc.AllocServer;
import com.sjq.mpush.util.MpushUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllocServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public AllocServer serverLauncher() {
        AllocServer allocServer = new AllocServer();
        MpushUtil.setAllocServer(allocServer);
        allocServer.init();
        return allocServer;
    }
}