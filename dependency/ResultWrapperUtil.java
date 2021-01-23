package com.smartebao.businesscenter.framework.utils;

import {}.ConstantCode;
import {}.ConstantViewName;
import org.springframework.web.servlet.ModelAndView;

/**
 * 返回前端数据封装
 *
 * @author <a href="mailto:mailto:wyr95626@163.com">CareyWYR</a>
 * @date 2021/1/23
 */
public class ResultWrapperUtil {

    public static ModelAndView returnSuccess(Object object) {
        ModelAndView mv = new ModelAndView(ConstantViewName.jsonView);
        mv.addObject("code", ConstantCode.SUCCESS);
        mv.addObject("data",object==null?"{}":object);
        return mv;
    }

    public static ModelAndView returnSuccess() {
        ModelAndView mv = new ModelAndView(ConstantViewName.jsonView);
        mv.addObject("code", "SUCCESS");
        mv.addObject("data","{}");
        return mv;
    }

    public static ModelAndView returnFail(String errorMsg) {
        ModelAndView mv = new ModelAndView(ConstantViewName.jsonView);
        mv.addObject("code", ConstantCode.ERROR);
        mv.addObject("data","{}");
        mv.addObject("errorMsg",errorMsg);
        return mv;
    }
}
