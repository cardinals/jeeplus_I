package com.jeeplus.modules.business.api.base;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jeeplus.modules.business.api.annotation.Json;
import lombok.Builder;
import lombok.experimental.Tolerate;

import java.text.SimpleDateFormat;

@Builder
public class CustomerJsonSerializer {

    static final String DYNC_INCLUDE = "DYNC_INCLUDE";
    static final String DYNC_FILTER = "DYNC_FILTER";

    @Builder.Default
    ObjectMapper mapper = new ObjectMapper();

    @JsonFilter(DYNC_FILTER)
    interface DynamicFilter {
    }

    @JsonFilter(DYNC_INCLUDE)
    interface DynamicInclude {
    }

    @Tolerate
    public CustomerJsonSerializer(){mapper = new ObjectMapper();}

    /**
     * @param clazz   需要设置规则的Class
     * @param include 转换时包含哪些字段
     * @param filter  转换时过滤哪些字段
     */
    public void filter(Class<?> clazz, String include, String filter) {
        if (clazz == null || clazz.equals(Json.class)) return;
        if (include != null && include.length() > 0) {
            SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().addFilter(DYNC_INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(include.split(",")));
            mapper.setFilterProvider(simpleFilterProvider);
            mapper.addMixIn(clazz, DynamicInclude.class);
        } else if (filter != null && filter.length() > 0) {
            SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().addFilter(DYNC_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(filter.split(",")));
            mapper.setFilterProvider(simpleFilterProvider);
            mapper.addMixIn(clazz, DynamicFilter.class);
        }
    }

    public String toJson(Object object, SimpleDateFormat sdf) throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); //忽略null
        mapper.setDateFormat(sdf);  //设置统一的时间格式
        return mapper.writeValueAsString(object);
    }

    public String toJson(Object object) throws JsonProcessingException {
        return toJson(object, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}