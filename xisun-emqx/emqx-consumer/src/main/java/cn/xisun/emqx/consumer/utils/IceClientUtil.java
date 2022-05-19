package cn.xisun.emqx.consumer.utils;

import Ice.Communicator;
import Ice.InitializationData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/29 17:23
 * @description ICE客户端工具
 */
@Slf4j
public class IceClientUtil {
    private static volatile Communicator mIc = null;

    private IceClientUtil() {

    }

    public static Communicator getIceClient() {
        if (mIc == null) {
            synchronized (IceClientUtil.class) {
                if (mIc == null) {
                    InitializationData initData = new InitializationData();
                    initData.properties = Ice.Util.createProperties();
                    initData.properties.setProperty("Ice.MessageSizeMax", "10240");
                    mIc = Ice.Util.initialize(initData);
                    log.debug("mIc address: {}", mIc);
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        log.debug("Execute Shutdown Hook, mIc address: {}", mIc);
                        mIc.destroy();
                    }));
                }
            }
        }
        return mIc;
    }
}
