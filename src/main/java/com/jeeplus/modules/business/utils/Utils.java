package com.jeeplus.modules.business.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
public class Utils {

    /**
     * 将Bean 转换为 Map
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }


    /**
     * 将Json转换为Map
     * @param json
     * @return
     */
    public static  Map jsonToMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(json, Map.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json转换为list
     * @param listJson
     * @return
     */
    public static List jsonToList(String listJson){
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map> list = mapper.readValue(listJson,new TypeReference<List<Map>>() { });
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
