package com.sr.aop;

import com.sr.exception.StatusException;
import com.sr.common.ReturnCodeBuilder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/9/30 15:28
 */
@ControllerAdvice
public class ExceptionAdvice {
    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        //前端传入的时间格式必须是"yyyy-MM-dd HH:mm:ss"效果!
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Map<String, Object> statusExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ReturnCodeBuilder.Builder builder;
        if (e instanceof StatusException) {
            StatusException exception = (StatusException) e;
            response.setStatus(exception.getCode());
            builder = ReturnCodeBuilder.failedBuilder(exception);
        } else if (e instanceof  RuntimeException) {
            response.setStatus(700);
            builder = ReturnCodeBuilder.failedBuilder().message("[Runtime]" + e.getMessage());
        } else {
            response.setStatus(700);
            builder = ReturnCodeBuilder.failedBuilder().message(e.getMessage());
        }
        return builder.url(request.getRequestURL().toString())
                .buildMap();
    }
}
