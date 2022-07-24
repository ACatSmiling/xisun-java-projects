package cn.xisun.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/20 0:37
 * @description 员工
 */
@Data
@NoArgsConstructor
public class Employee {
    private Integer id;

    private String empName;

    private String sex;

    private Integer age;

    /**
     * 多对一：Employee实例，对应一个Department实例
     */
    private Department department;
}
