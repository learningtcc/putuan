package com.advanpro.putuan.utils.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.type.TypeFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Retina.Ye
 */
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
    private static final Log logger = LogFactory.getLog(JsonUtils.class);

    static {
        // 不序列化map中和bean中的null字段
        mapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        // 使用自定义格式化格式
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SerializationConfig cfg = mapper.getSerializationConfig().withDateFormat(sdf);
        mapper.setSerializationConfig(cfg);
        DeserializationConfig deserCfg = mapper.getDeserializationConfig().withDateFormat(sdf);
        deserCfg.set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 转换不存在的字段时不抛出异常
        mapper.setDeserializationConfig(deserCfg);
    }

    /**
     * 将对象转成json.
     *
     * @param obj 对象
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str = mapper.writeValueAsString(obj);
            return str;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转成json.不会抛出错误信息 便于日志打印使用
     *
     * @param obj 对象
     * @return
     */
    public static String toJsonNotError(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str = mapper.writeValueAsString(obj);
            return str;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * json转List.
     *
     * @param content   json数据
     * @param valueType 泛型数据类型
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <T> List<T> toListObject(String content, Class<T> valueType) {
        if (content == null || content.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(content, TypeFactory.collectionType(List.class, valueType));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> List<T> toObject(List<String> jsonList, Class<T> valueType) {
        if (jsonList == null || jsonList.isEmpty()) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (String json : jsonList) {
            list.add(JsonUtils.toObject(json, valueType));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String content) {
        return JsonUtils.toObject(content, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static Set<Object> toSet(String content) {
        return JsonUtils.toObject(content, Set.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
        return JsonUtils.toObject(json, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> toSet(String json, Class<T> clazz) {
        return JsonUtils.toObject(json, Set.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toNotNullMap(String json) {
        Map<String, Object> map = JsonUtils.toObject(json, Map.class);
        if (map == null) {
            map = new LinkedHashMap<String, Object>();
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toNotNullMap(String json, Class<T> clazz) {
        Map<String, T> map = JsonUtils.toObject(json, Map.class);
        if (map == null) {
            map = new LinkedHashMap<String, T>();
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> toNotNullSet(String json, Class<T> clazz) {
        Set<T> set = JsonUtils.toObject(json, Set.class);
        if (set == null) {
            set = new LinkedHashSet<T>();
        }
        return set;
    }

    /**
     * 类型转换.
     *
     * @param obj
     * @param clazz
     * @return
     */
    public static <T> T convert(Object obj, Class<T> clazz) {
        String json = JsonUtils.toJson(obj);
        return toObject(json, clazz);
    }

    /**
     * 将Json转换成对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {

    }

}