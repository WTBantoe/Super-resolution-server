package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/15 17:05
 */
@RequestMapping("/comment")
@Api(tags = {"评论管理"})
@RestController
public class CommentController {
    @Autowired
    HttpUtil httpUtil;

    @Autowired
    CommentService commentService;

    @ApiOperation(
            value = "发表评论",
            notes = "发表评论"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> post(HttpServletRequest request,
                                    @RequestParam(value = "message") String message,
                                    @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(request));
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(commentService.post(uid,message,multipartFiles))
                .buildMap();
    }

    @ApiOperation(
            value = "查看评论",
            notes = "查看评论"
    )
    @RequestMapping(
            value = "all",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> getAllComment(){
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(commentService.getAllComment())
                .buildMap();
    }

    @ApiOperation(
            value = "根据用户id查看评论",
            notes = "根据用户id查看评论"
    )
    @RequestMapping(
            value = "uid",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> getCommentByUid(@RequestParam(value = "uid") Long uid){
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(commentService.getAllCommentByUid(uid))
                .buildMap();
    }

    @ApiOperation(
            value = "用户查看自己的评论",
            notes = "用户查看自己的评论"
    )
    @RequestMapping(
            value = "user",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> getUserComment(HttpServletRequest request){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(request));
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(commentService.getAllCommentByUid(uid))
                .buildMap();
    }
}
