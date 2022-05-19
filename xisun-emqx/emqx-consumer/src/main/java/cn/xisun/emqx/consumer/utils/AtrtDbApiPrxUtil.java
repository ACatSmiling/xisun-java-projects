package cn.xisun.emqx.consumer.utils;

import Ice.Communicator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/29 17:23
 * @description 安泰时许数据库客户端工具
 */
public class AtrtDbApiPrxUtil {
    /*private static volatile ATRTDBAPIPrx mProxy = null;

    private AtrtDbApiPrxUtil() {

    }

    public static ATRTDBAPIPrx getAtrtDbApiPrx() {
        if (mProxy == null) {
            synchronized (AtrtDbApiPrxUtil.class) {
                if (mProxy == null) {
                    Communicator mIc = IceClientUtil.getIceClient();

                    try {
                        InputStream is = MqttClientUtil.class.getClassLoader().getResourceAsStream("atrtdb.properties");
                        Properties pros = new Properties();
                        pros.load(is);

                        String url = pros.getProperty("url");
                        String port = pros.getProperty("port");

                        String endPoint = "ATRTDBServer:default -h " + url + " -p " + port + " -t 30000 -z";
                        mProxy = ATRTDBAPIPrxHelper.checkedCast(mIc.stringToProxy(endPoint));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mProxy;
    }*/
}
