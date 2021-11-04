package com.sr.service.impl;

import com.sr.common.EntityMapConvertor;
import com.sr.dao.HistoryMapper;
import com.sr.entity.History;
import com.sr.entity.example.HistoryExample;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/2 16:49
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryMapper historyMapper;

    @Override
    public List<Map<String, Object>> getUserHistoryListByModifyTimeDESC(Long uid, Long page, Integer pageSize) {
        HistoryExample historyExample = new HistoryExample();
        HistoryExample.Criteria criteria = historyExample.createCriteria();
        criteria.andUidEqualTo(uid);
        historyExample.setOrderByClause("gmt_modify DESC");

        if (page > 0) {
            Long offset = (page - 1L) * pageSize;
            historyExample.setLimit(pageSize);
            historyExample.setOffset(offset);
        }

        List<History> histories = historyMapper.selectByExample(historyExample);

        return EntityMapConvertor.entityList2MapList(histories);
    }

    @Override
    public Map<String, Object> post(History history) {
        try {
            long id = historyMapper.insertSelective(history);
            history.setId(id);
        }
        catch (Exception e){
            throw new StatusException(StatusEnum.HISTORY_INSERT_FAIL);
        }
        return EntityMapConvertor.entity2Map(history);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> postList(List<History> histories) {
        List<Map<String, Object>> historyMapList = new ArrayList<>(); 
        for (History history : histories){
            historyMapList.add(post(history));
        }
        return historyMapList;
    }
}
