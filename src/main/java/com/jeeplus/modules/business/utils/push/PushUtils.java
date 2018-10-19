package com.jeeplus.modules.business.utils.push;

import com.jeeplus.common.config.Global;
import com.jeeplus.modules.business.utils.push.android.AndroidUnicast;

import java.util.Iterator;
import java.util.Map;


public class PushUtils {
    private static final String APPKEY_ANDROID = Global.getConfig("push.android.key");
    private static final String APPMASTERSECRET_ANDROID = Global.getConfig("push.android.secret");
    private static final boolean TESTMODE = "true".equalsIgnoreCase(Global.getConfig("push.test"));
    private static final PushClient CLIENT = new PushClient();

    /**
     *
     * @param deviceToken
     * @param title 标题
     * @param ticker
     * @param text  内容
     * @param customizedField   自定义属性
     * @throws Exception
     */
    public static void sendAndroidUnicast(String deviceToken, String title, String ticker,String text, Map<String,String> customizedField) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(APPKEY_ANDROID, APPMASTERSECRET_ANDROID);
        unicast.setDeviceToken(deviceToken);
        unicast.setTicker(ticker);
        unicast.setTitle(title);
        unicast.setText(text);
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);

        if(TESTMODE){
            unicast.setTestMode(); //测试模式
        }else{
            unicast.setProductionMode();//正式模式
        }


        if(customizedField!=null){
            Iterator<String> iterator = customizedField.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                unicast.setExtraField(key, customizedField.get(key));
            }
        }
        CLIENT.send(unicast);
    }
}
