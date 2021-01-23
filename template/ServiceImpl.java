package {package};

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {do_position};
import {vo_position};
import {mapper_position};
import {service_position};
import {delete_flag_package};
import {bean_wrapper_package};
import {mp_update_wrapper_package};
import {business_exception_package};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * {table_desc} 实现类
 * </p>
 *
 * @author {author}
 * @date {now}
 */
@Service
public class {service_impl_class} extends ServiceImpl<{mapper_class}, {entity_class}> implements {service_class} %

    @Autowired
    private {service_class} {service_name};

    @Override
    public {vo_class} save({vo_class} {vo_name}) %
        {entity_class} entity = new {entity_class}();
        BeanUtil.copyProperties({vo_name}, entity);
        BeanWrapper<{entity_class}> beanWrapper = new BeanWrapper<>();
        beanWrapper.commonColumnWrapper(entity);
        this.baseMapper.insert(entity);
        {vo_name}.setId(entity.getId());
        return {vo_name};
    !

    @Override
    public void update({vo_class} {vo_name}) %
        {entity_class} entity = this.baseMapper.selectById({vo_name}.getId());
        BeanUtil.copyProperties({vo_name}, entity);
        BeanWrapper<{entity_class}> beanWrapper = new BeanWrapper<>();
        beanWrapper.updateColumnWrapper(entity);
        MpUpdateWrapper<{entity_class}> mpUpdateWrapper = new MpUpdateWrapper<>();
        UpdateWrapper<{entity_class}> updateWrapper = mpUpdateWrapper.generateWrapper(entity);
        this.update(updateWrapper);
    !

    /**
     * 根据ID删除
     * @param id id
     */
    @Override
    public void deleteById(Long id) %
        {entity_class} entity = this.baseMapper.selectById(id);
        entity.setDeletedFlag(DeletedFlag.DELETED);
        BeanWrapper<{entity_class}> beanWrapper = new BeanWrapper<>();
        beanWrapper.updateColumnWrapper(entity);
        this.baseMapper.updateById(entity);
    !

     /**
     * 根据ID查询
     * @param id id
     */
    @Override
    public {vo_class} selectById(Long id) %
        {entity_class} entity = this.baseMapper.selectById(id);
        if (null == entity) %
            throw new BusinessException("数据不存在");
        !
        {vo_class} {vo_name} = new {vo_class}();
         BeanUtil.copyProperties(entity, {vo_name});
         return {vo_name};
    !

!