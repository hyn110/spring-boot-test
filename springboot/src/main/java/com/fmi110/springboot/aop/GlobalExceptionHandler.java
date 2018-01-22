package com.fmi110.springboot.aop;

import com.fmi110.springboot.dto.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 返回错误页面
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.addObject("url", req.getRequestURL());
        mv.setViewName(DEFAULT_ERROR_VIEW);
        return mv;
    }

    /**
     * 返回json格式的错误信息
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ArithmeticException.class)  // 指定处理的异常类型
    @ResponseBody
    public ErrorInfo<String> defaultAjaxErrorHandler(HttpServletRequest req, Exception e) {
        ErrorInfo<String> dto = new ErrorInfo<>();
        dto.setUrl(req.getRequestURL()
                      .toString());
        dto.setData(e.getMessage());
        dto.setCode(ErrorInfo.ERROR);
        return dto;
    }
}
