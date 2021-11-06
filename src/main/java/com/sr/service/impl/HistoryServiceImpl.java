package com.sr.service.impl;

import com.sr.common.EntityMapConvertor;
import com.sr.common.HttpUtil;
import com.sr.dao.HistoryMapper;
import com.sr.entity.History;
import com.sr.entity.example.HistoryExample;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.HistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    HttpUtil httpUtil;

    @Override
    public List<Map<String, Object>> getUserHistoryListByModifyTimeDESC(Long uid, Long page, Integer pageSize, String tag) {
        HistoryExample historyExample = new HistoryExample();
        HistoryExample.Criteria criteria = historyExample.createCriteria();
        criteria.andUidEqualTo(uid);
        if (!StringUtils.isEmpty(tag)){
            criteria.andTagEqualTo(tag);
        }

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

    @Override
    public List<String> getUserTags(Long uid) {
        return historyMapper.getUserTags(uid);
    }

    @Override
    public History preHistory(Long uid, String material, Integer type, String tag)
    {
        History history = new History();
        history.setUid(uid);
        history.setRawMaterial(material);
        history.setType(type);
        history.setTag(tag);
        return history;
    }

    @Override
    public History postHistory(History history,String result, Long span)
    {
        history.setResult(result);
        history.setSpan(span);
        return history;
    }

    @Override
    public void addHistory(History history)
    {
        historyMapper.insert(history);
    }
}
