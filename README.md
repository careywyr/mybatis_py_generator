# mybatis_py_generator

这是一个基于mybatis-plus,可以自动生成后段CRUD代码的工具，包括entity,vo,dao,service,controller。

### Java项目需要的依赖

| 依赖                            | 版本  |
| ------------------------------- | :---- |
| mybatis-plus-boot-starter 3.4.0 | 3.4.0 |
| hutool                          | 5.2.0 |

另外使用了自己平常用的Java工具类，这里做下解释

| 工具类            | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| BeanWrapper       | 对于审计字段（如createTime,creator）等字段进行统一赋值       |
| MpUpdateWrapper   | 最新版mp去掉了updateByAllColumn方法，导致空字段无法更新，    |
| ResultWrapperUtil | 包装一下controller的返回值，这里返回的是ModelAndView。习惯用自定义一个范型类的改一下这个工具和controller的返回就好了。里面导入的包也包含了自己用的常量，这里没有提供，因为这个工具不具有普适性我也就不提供常量了。 |

### 使用方式

修改template目录下的config.conf。database部分不用多说，就是数据库的配置。base部分指的是各个类型文件的包地址和文件夹地址等基础配置。

| 配置项                     | 解释                                                         |
| -------------------------- | ------------------------------------------------------------ |
| author                     | 作者                                                         |
| table                      | 要生成的表名，多个以逗号隔开                                 |
| module_package             | 要生成的模块包路径 ，注意最后要有个点。如cn.leafw.business.  |
| delete_flag_package        | 是否删除这个常量类的地址。如cn.leafw.contant.DeleteFlag      |
| bean_wrapper_package       | 上文说的BeanWrapper地址，如cn.leafw.utils.BeanWrapper        |
| mp_update_wrapper_package  | 上文说的MpUpdateWrapper地址，如cn.leafw.utils.MpUpdateWrapper |
| business_exception_package | 自定义业务异常地址，如cn.leafw.exceptions.BusinessException。一般项目中会有统一处理异常的处理，若不需要可删除ServiceImpl模版中的这部分代码。 |
| module_dir_root            | 模块包的文件夹地址，如/Users/Leafw/Document/project/test/src/java/main/cn/leafw/business/ |
| xml_dir_root               | mapper的xml资源放的文件夹地址，一般在resource下面，我这里没按照业务分类了 |
| use_swagger                | 是否使用swagger注解，是的话写1，不是写其他任何都可以。用了的话vo和controller会有相应的注解。 |

各位根据自己的情况，可以修改template下的模版，但占位符的名字如果改了记得代码中也要改下。上述的三个工具类如果不需要也可以在模版里去掉用到的代码即可。因为是想到啥就写啥所以代码有点乱，尤其是各种class和name分不清楚，在加上这几个工具类写死了，所以后面有空（希望吧）会调整下配置项以及代码的可读性。

