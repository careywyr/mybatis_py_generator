package {};

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;

/**
 * 最新版mp去掉了updateByAllColumn方法，导致空字段无法更新
 * 这里写个方法进行处理
 *
 * @author <a href="mailto:mailto:wyr95626@163.com">CareyWYR</a>
 * @date 2021/1/23
 */
public class MpUpdateWrapper<T> {

    public UpdateWrapper<T> generateWrapper(T t) {
        String name = t.getClass().getName();
        try {
            Class<?> aClass = Class.forName(name);
            Method getId = aClass.getDeclaredMethod("getId");
            Long id = (Long) getId.invoke(t);
            Field[] fields = aClass.getDeclaredFields();
            UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.equals("id") || fieldName.equals("serialVersionUID")) {
                    continue;
                }
                // 得到对应属性的值set进去
                // 属性首字母大写
                String firstUpperFieldName = fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
                // 下划线属性
                String underlineFieldName = camelToUnderline(fieldName);
                String getMethodName = "get" + firstUpperFieldName;
                Method getMethod = aClass.getDeclaredMethod(getMethodName);
                Object value = getMethod.invoke(t);
                updateWrapper.set(underlineFieldName, value);
            }
            return updateWrapper;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 驼峰转下划线
     * @param param
     * @return
     */
    private static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            //统一都转小写
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
