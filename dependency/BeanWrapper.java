package {};

import {}.DeletedFlag;
import {}.SSOUserContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 封装数据库实体
 *
 * @author <a href="mailto:mailto:wyr95626@163.com">CareyWYR</a>
 * @date 2021/1/23
 */
@Slf4j
public class BeanWrapper<T> {

    /**
     * 所有审计字段
     * @param t t
     */
    public void commonColumnWrapper(T t){
        try {
            String name = t.getClass().getName();
            Class<?> aClass = Class.forName(name);
            Method setModifyTime = aClass.getDeclaredMethod("setModifyTime", Date.class);
            Method setModifierName = aClass.getDeclaredMethod("setModifierName", String.class);
            Method setModifierId = aClass.getDeclaredMethod("setModifierId", String.class);
            Method setCreateTime = aClass.getDeclaredMethod("setCreateTime", Date.class);
            Method setCreatorName = aClass.getDeclaredMethod("setCreatorName", String.class);
            Method setCreatorId = aClass.getDeclaredMethod("setCreatorId", String.class);
            Method setDeletedFlag = aClass.getDeclaredMethod("setDeletedFlag", String.class);
            Method setOrgId = aClass.getDeclaredMethod("setOrgId", Long.class);
            setModifierId.invoke(t, SSOUserContextHolder.getCurrentUser().getUserId());
            setCreatorId.invoke(t, SSOUserContextHolder.getCurrentUser().getUserId());
            setOrgId.invoke(t, SSOUserContextHolder.getCurrentUser().getOrgId());
            setDeletedFlag.invoke(t, DeletedFlag.EXIST);
            setModifyTime.invoke(t, new Date());
            setModifierName.invoke(t, SSOUserContextHolder.getCurrentUser().getUserName());
            setCreateTime.invoke(t, new Date());
            setCreatorName.invoke(t, SSOUserContextHolder.getCurrentUser().getUserName());
        } catch (Exception e) {
            log.error("用户未登陆!");
            e.printStackTrace();
        }
    }

    /**
     * 仅封装更新字段
     * @param t t
     */
    public void updateColumnWrapper(T t){
        try {
            String name = t.getClass().getName();
            Class<?> aClass = Class.forName(name);
            Method setModifyTime = aClass.getDeclaredMethod("setModifyTime", Date.class);
            Method setModifierName = aClass.getDeclaredMethod("setModifierName", String.class);
            Method setModifierId = aClass.getDeclaredMethod("setModifierId", String.class);
            setModifierId.invoke(t, SSOUserContextHolder.getCurrentUser().getUserId());
            setModifyTime.invoke(t, new Date());
            setModifierName.invoke(t, SSOUserContextHolder.getCurrentUser().getUserName());
        } catch (Exception e) {
            log.error("用户未登陆!");
            e.printStackTrace();
        }
    }
}
