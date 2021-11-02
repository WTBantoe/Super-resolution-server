package com.sr.common;

import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/2 15:57
 */
public class EntityMapConvertor {
    public static Map<String, Object> entity2Map (Object entity) {
        if (entity == null) {
            return new HashMap<>();
        }
        try {
            Map<String, Object> map = BeanUtils.describe(entity);
            // 移除key=class
            map.remove("class");
            return map;
        } catch (Exception e) {
            throw new StatusException(StatusEnum.ENTITY_CONVERT_FAIL);
        }
    }
}
