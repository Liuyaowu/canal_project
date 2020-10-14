package com.mobei.canal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换器
 */
public class TypeConvertHandler {
    public static final Map<Class, String> BEAN_FIELD_TYPE;

    static {
        BEAN_FIELD_TYPE = new HashMap<>(8);
        BEAN_FIELD_TYPE.put(Integer.class, "Integer");
        BEAN_FIELD_TYPE.put(Long.class, "Long");
        BEAN_FIELD_TYPE.put(Double.class, "Double");
        BEAN_FIELD_TYPE.put(String.class, "String");
        BEAN_FIELD_TYPE.put(Date.class, "java.util.Date");
        BEAN_FIELD_TYPE.put(java.sql.Date.class, "java.sql.Date");
        BEAN_FIELD_TYPE.put(java.sql.Timestamp.class, "java.sql.Timestamp");
        BEAN_FIELD_TYPE.put(java.sql.Time.class, "java.sql.Time");
    }

    protected static final Integer parseToInteger(String source) {
        if (isSourceNull(source)) {
            return null;
        }
        return Integer.valueOf(source);
    }

    protected static final Long parseToLong(String source) {
        if (isSourceNull(source)) {
            return null;
        }
        return Long.valueOf(source);
    }

    protected static final Double parseToDouble(String source) {
        if (isSourceNull(source)) {
            return null;
        }
        return Double.valueOf(source);
    }

//    protected static final Date parseToDate(String source) {
//        if (isSourceNull(source)) {
//            return null;
//        }
//        if (source.length() == 10) {
//            source = source + " 00:00:00";
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
//        return Date.valueOf(source);
//    }
//
//    protected static final Double parseToDouble(String source) {
//        if (isSourceNull(source)) {
//            return null;
//        }
//        return Double.valueOf(source);
//    }

    private static boolean isSourceNull(String source) {
        if (source == "" || source == null) {
            return true;
        }
        return false;
    }


}
