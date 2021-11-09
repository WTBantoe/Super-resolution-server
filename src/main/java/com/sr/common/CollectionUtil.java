package com.sr.common;

import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author cyh
 * @Date 2021/11/7 14:53
 */
public class CollectionUtil
{
    public static <T> boolean isListUnique(List<T> list)
    {
        if (CollectionUtils.isEmpty(list) || list.size() > 1)
        {
            throw new StatusException(StatusEnum.LIST_IS_EMPTY_OR_NOT_UNIQUE);
        }
        return true;
    }

    public static <T> T getUniqueObjectFromList(List<T> list)
    {
        if (isListUnique(list))
        {
            return list.get(0);
        }
        throw new StatusException(StatusEnum.LIST_IS_EMPTY_OR_NOT_UNIQUE);
    }
}
