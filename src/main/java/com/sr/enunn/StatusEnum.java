package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/9/30 15:54
 */
public enum StatusEnum {
    SUCCESS(200,"SUCCESS : 请求成功"),
    FAIL(700,"FAIL : 请求失败"),
    NO_AUTH(401,"NO_AUTH : 未经验证"),
    FORBIDDEN(403,"FORBIDDEN : 权限不足"),
    UNDEFINED_EXCEPTION(1000,"UNDEFINED_EXCEPTION : 未知错误"),
    INVALID_TELEPHONE_NUMBER(501,"INVALID_TELEPHONE_NUMBER : 非法手机号"),
    INVALID_VERIFY_CODE(502,"INVALID_VERIFY_CODE : 验证码错误"),
    USER_NOT_UNIQUE(503,"USER_NOT_UNIQUE : 数据库异常，用户不唯一！"),
    USER_NOT_EXIST(504,"USER_NOT_EXIST : 用户尚未注册!"),
    USER_ALREADY_EXIST(505,"USER_ALREADY_EXIST : 用户已注册!"),
    ENTITY_CONVERT_FAILED(506,"ENTITY_CONVERT_FAIL : 实体类转换map失败！"),
    USER_REGISTER_FAILED(507,"USER_REGISTER_FAIL : 用户注册失败！"),
    USER_REGISTER_FAIL_WITH_VIP(508,"USER_REGISTER_FAIL_WITH_VIP : 用户注册，创建VIP账户失败！"),
    COULD_NOT_FIND_PROCESSED_PICTURE(509,"COULD_NOT_FIND_PROCESSED_PICTURE : 找不到处理完的图片！"),
    COULD_NOT_DOWNLOAD_PICTURE(510,"COULD_NOT_DOWNLOAD_PICTURE : 无法下载图片!"),
    SAVE_FILE_FAILED(511, "SAVE_FILE_FAILED : 文件保存失败!"),
    PICTURE_NOT_UPLOAD(512,"PICTURE_NOT_UPLOAD : 图片未上传！"),
    INVALID_FILE_TYPE(513,"INVALID_FILE_TYPE : 无法识别的文件类型！"),
    VIDEO_NOT_UPLOAD(514,"VIDEO_NOT_UPLOAD : 视频未上传！"),
    COULD_NOT_FIND_PROCESSED_VIDEO(515,"COULD_NOT_FIND_PROCESSED_VIDEO : 找不到处理完的视频！"),
    VERIFY_CODE_FAILED(516,"VERIFY_CODE_FAILED : 校验验证码失败！"),
    HISTORY_INSERT_FAIL(517,"HISTORY_INSERT_FAIL : 历史记录插入失败！"),
    TOKEN_EXPIRE(518,"TOKEN_EXPIRE : 用户token过期！请重新登录"),
    LIST_IS_EMPTY_OR_NOT_UNIQUE(519,"LIST_IS_EMPTY_OR_NOT_UNIQUE : 列表为空或不唯一！"),
    FREE_TIME_NOT_ENOUGH(520,"FREE_TIME_NOT_ENOUGH : 免费次数不足！"),
    HISTORY_NOT_EXIST(521,"HISTORY_NOT_EXIST : 历史记录不存在！"),
    NOT_CORRECT_USER(522,"NOT_CORRECT_USER : 不是正确的用户！"),
    HISTORY_DELETE_FAILED(523,"HISTORY_DELETE_FAILED : 历史用户删除失败！"),
    WALLET_HASH_INCORRECT(524,"WALLET_HASH_INCORRECT : 钱包校验失败！"),
    BALANCE_NOT_ENOUGH(525,"BALANCE_NOT_ENOUGH : 钱包余额不足！"),
    ;
    int code;
    String description;

    public static StatusEnum getByCode (int code) {
        for (StatusEnum statusEnum : StatusEnum.class.getEnumConstants()) {
            if (code == statusEnum.getCode()) {
                return statusEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    StatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
