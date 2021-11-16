package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/3 21:01
 */
public enum MediaTypeEnum {
    PICTURE(0,"图片"),
    VIDEO(1,"视频"),
    AVATAR(2, "头像"),
    COMMENT(3, "评论"),
    ;

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    Integer code;
    String description;

    MediaTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
