package {package};

import {vo_position};
import {service_position};
import com.smartebao.businesscenter.framework.utils.ResultWrapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * {table_desc} 实现类
 * </p>
 *
 * @author {author}
 * @date {now}
 */
@Api(tags = "{table_desc}相关接口")
@RestController
@Validated
public class {controller_class} %

    @Autowired
    private {service_class} {service_name};

    /**
     * 保存{table_desc}
     * @param {vo_name} {vo_name}
     * @return null
     */
    @ApiOperation(value = "保存{table_desc}")
    @PostMapping("/")
    public ModelAndView save(@Valid @RequestBody {vo_class} {vo_name}) %
        {service_name}.save({vo_name});
        return ResultWrapperUtil.returnSuccess();
    !

    /**
     * 修改{table_desc}
     * @param {vo_name} {vo_name}
     * @return null
     */
    @ApiOperation(value = "修改{table_desc}")
    @PutMapping("/")
    public ModelAndView update(@Valid @RequestBody {vo_class} {vo_name})%
        {service_name}.update({vo_name});
        return ResultWrapperUtil.returnSuccess();
    !

    /**
     * 删除一个{table_desc}
     * @param id id
     * @return null
     */
    @ApiOperation(value = "删除一个{table_desc}")
    @ApiImplicitParams(%
            @ApiImplicitParam(name = "id", value = "主建ID", required = true)!)
    @DeleteMapping("/")
    public ModelAndView delete(@RequestParam("id") Long id) %
        {service_name}.deleteById(id);
        return ResultWrapperUtil.returnSuccess();
    !

    /**
     * 查询{table_desc}
     * @param id id
     * @return 详情
     */
    @ApiOperation(value = "查询一个{table_desc}")
    @ApiImplicitParams(%
            @ApiImplicitParam(name = "id", value = "主建ID", required = true)!)
    @GetMapping("/%id!")
    public ModelAndView selectOne(@PathVariable("id") Long id) %
        {vo_class} {vo_name} = {service_name}.selectById(id);
        return ResultWrapperUtil.returnSuccess({vo_name});
    !
!