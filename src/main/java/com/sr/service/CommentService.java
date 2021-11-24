package com.sr.service;

import com.sr.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/15 16:52
 */
public interface CommentService {
    List<Map<String, Object>> getAllComment();

    Map<String, Object> post(Long uid, String message, MultipartFile[] multipartFiles);

    List<Map<String, Object>> getAllCommentByUid(Long uid);
}
