package {package};

import com.baomidou.mybatisplus.extension.service.IService;
import {do_position};
import {vo_position};

import java.util.List;

@Service
public interface {service_name} extends IService<{do_name}> {

    /**
     * 新增
     * @param {vo_name} {vo_name}
     * @return 主键
     */
    {vo_class} saveByVo({vo_class} {vo_name});

    /**
     * 修改
     * @param {vo_name} {vo_name}
     * @return 主键
     */
    void updateByVo({vo_class} {vo_name});

    /**
     * 根据ID删除
     * @param id id
     */
    void deleteById(Long id);

}