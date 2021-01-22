package {package};

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * {table_desc}
 * </p>
 *
 * @author {author}
 * @date {now}
 */
@Data
@Accessors(chain = true)
@TableName("{table_name}")
@EqualsAndHashCode(callSuper = true)
public class {do_name} extends Model<{do_name}>