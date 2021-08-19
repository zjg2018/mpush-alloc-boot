package com.sjq.mpush.util;


import com.sjq.mpush.alloc.AllocServer;

/**
 * @Auther: zjg
 * @Date: 2021/08/18/21:39
 * @Description:
 */
public class MpushUtil {

    private static  AllocServer allocServer;


    public static AllocServer getAllocServer() {
        if (allocServer == null) {
            return null;
        }
        return allocServer;
    }
    public static void setAllocServer(AllocServer allocServer) {
        MpushUtil.allocServer = allocServer;
    }
}
