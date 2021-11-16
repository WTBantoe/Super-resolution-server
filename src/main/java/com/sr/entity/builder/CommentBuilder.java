package com.sr.entity.builder;

import com.sr.entity.Comment;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/15 17:00
 */
public final class CommentBuilder {
    private Long id;
    private Long uid;
    private String message;
    private String material;
    private Date gmtCreate;
    private Date gmtModify;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommentBuilder withUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public CommentBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public CommentBuilder withMaterial(String material) {
        this.material = material;
        return this;
    }

    public CommentBuilder withGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public CommentBuilder withGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUid(uid);
        comment.setMessage(message);
        comment.setMaterial(material);
        comment.setGmtCreate(gmtCreate);
        comment.setGmtModify(gmtModify);
        return comment;
    }
}
