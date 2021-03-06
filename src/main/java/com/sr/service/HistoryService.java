package com.sr.service;

import com.sr.entity.History;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author cyh
 * @Date 2021/11/2 16:47
 */
@Service
public interface HistoryService {
    List<Map<String, Object>> getUserHistoryListByModifyTimeDESC (Long uid, Long page, Integer pageSize, String tag);

    Map<String, Object> post (History history);

    List<Map<String, Object>> postList (List<History> history);

    List<String> getUserTags(Long uid);

    boolean deleteById(Long id, Long uid);

    long getCountByUid(Long uid);

    long deleteByIdList(List<Long> historyIds, Long uid);
}
