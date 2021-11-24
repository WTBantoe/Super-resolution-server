package com.sr.service.impl;

import com.sr.common.EntityMapConvertor;
import com.sr.dao.CommentMapper;
import com.sr.entity.Comment;
import com.sr.entity.builder.CommentBuilder;
import com.sr.entity.dto.CommentDTO;
import com.sr.entity.example.CommentExample;
import com.sr.service.CommentService;
import com.sr.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/15 16:53
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UploadService uploadService;

    public static String COMMENT_PATH;

    @Value("${comment.path}")
    public void setCommentPath(String commentPath)
    {
        COMMENT_PATH = commentPath;
    }

    @Override
    public List<Map<String, Object>> getAllComment() {
        List<Comment> comments = commentMapper.selectByExample(new CommentExample());
        return EntityMapConvertor.entityList2MapList(CommentDTO.convertCommentListToCommentDTOList(comments));
    }

    @Override
    public Map<String, Object> post(Long uid, String message, MultipartFile[] multipartFiles) {
        String material = uploadService.uploadComment(multipartFiles);
        Comment comment = CommentBuilder.aComment()
                .withUid(uid)
                .withMaterial(material)
                .withMessage(message)
                .build();
        Long id = commentMapper.insertSelective(comment);
        comment.setId(id);
        return EntityMapConvertor.entity2Map(comment);
    }

    @Override
    public List<Map<String, Object>> getAllCommentByUid(Long uid) {
        List<Comment> comments = commentMapper.selectByExample(getCommentExampleByUid(uid));
        return EntityMapConvertor.entityList2MapList(CommentDTO.convertCommentListToCommentDTOList(comments));
    }

    private CommentExample getCommentExampleByUid(Long uid) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andUidEqualTo(uid);
        return commentExample;
    }

}
