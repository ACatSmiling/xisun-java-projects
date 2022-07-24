package cn.xisun.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/14 21:26
 * @description 用户
 */
@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private Integer status;

    /**
     * 使用 resultType 映射时，实体类的属性名，必须和数据库字段名一致
     */
    private Date create_time;

    private Date update_time;
}
