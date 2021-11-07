package com.sr.service;

import com.sr.entity.History;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    History preHistory(Long uid, String material, Integer type, String tag);
    History postHistory(History history,String result,Long span);
    void addHistory(History history);

}
