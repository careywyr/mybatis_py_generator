package {package};

import com.baomidou.mybatisplus.extension.service.IService;
import {do_position};
import {vo_position};

import java.util.List;

/**
 * <p>
 * {table_desc} 接口
 * </p>
 *
 * @author {author}
 * @date {now}
 */
public interface {service_name} extends IService<{do_name}>

    /**
     * 新增
     * @param {vo_name} {vo_name}
     * @return 主键
     */
    {vo_class} save({vo_class} {vo_name});

    /**
     * 修改
     * @param {vo_name} {vo_name}
     */
    void update({vo_class} {vo_name});

    /**
     * 根据ID删除
     * @param id id
     */
    void deleteById(Long id);

     /**
     * 根据ID查询
     * @param id id
     */
    {vo_class} selectById(Long id);
