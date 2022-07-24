package cn.xisun.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/20 0:38
 * @description 部门
 */
@Data
@NoArgsConstructor
public class Department {
    private Integer id;

    private String depName;

    /**
     * 一对多：Department实例，对应多个Employee实例，
     */
    private List<Employee> employees;
}
