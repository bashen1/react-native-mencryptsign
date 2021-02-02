package com.maochunjie.mencryptsign;

import com.facebook.react.bridge.*;

import java.util.Map;
import java.util.TreeMap;

public class RNReactNativeMencryptSignModule extends ReactContextBaseJavaModule {

    static {
        System.loadLibrary("mmbKey");
    }

    private final ReactApplicationContext reactContext;

    public RNReactNativeMencryptSignModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNReactNativeMencryptSign";
    }

    @ReactMethod
    public void makeSign(final ReadableMap data, final Promise p) {
        Map<String, Object> params = data.getMap("params").toHashMap();
        WritableMap map = Arguments.createMap();
        try {
            //参数名称的ASCII码表的顺序排序
            Map<String, Object> sortedMap = new TreeMap<String, Object>(params);
            //参数名和参数值拼装在一起，过滤掉空的
            String prestr = "";
            for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
                String keyStr = entry.getKey();
                String valueStr = (String) entry.getValue();
                if (!keyStr.equals("") && !valueStr.equals("")) {
                    prestr = prestr + keyStr + valueStr;
                }
            }
            String paramStr = prestr.toUpperCase();
            String md5Res = getToken(paramStr);

            map.putString("paramStr", paramStr);
            map.putString("sign", md5Res);
            map.putString("code", "1");
            p.resolve(map);
        } catch (Exception e) {
            map.putString("code", "0");
            p.resolve(map);
        }
    }

    public native String getToken(String param);
}
