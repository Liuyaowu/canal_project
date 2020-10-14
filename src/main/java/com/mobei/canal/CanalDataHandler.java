package com.mobei.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CanalDataHandler extends TypeConvertHandler {

    /**
     * 将binlog的记录解析为一个bean对象
     *
     * @param columnList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertToBean(List<CanalEntry.Column> columnList, Class<T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Field.setAccessible(fields, true);
            Map<String, Field> fieldMap = new HashMap<>(fields.length);
            for (Field field : fields) {
                fieldMap.put(field.getName().toLowerCase(), field);
            }
            if (fieldMap.containsKey("serialVersionUID")) {
                fieldMap.remove("serialVersionUID".toLowerCase());
            }

            for (CanalEntry.Column column : columnList) {
                String columnName = column.getName();
                String columnValue = column.getValue();

                if (fieldMap.containsKey(columnName)) {
                    Field field = fieldMap.get(columnName);
                    Class<?> type = field.getType();
                    if (BEAN_FIELD_TYPE.containsKey(type)) {
                        switch (BEAN_FIELD_TYPE.get(type)) {
                            case "Integer":
                                field.set(bean, parseToInteger(columnValue));
                                break;
                            case "Long":
                                field.set(bean, parseToLong(columnValue));
                                break;
                        }
                    } else {
                        field.set(bean, parseObj(columnValue));
                    }
                }
            }
        } catch (Exception exception) {
            log.error("e");
        }
        return bean;
    }

    private static Object parseObj(String columnValue) {

        return null;
    }


}
