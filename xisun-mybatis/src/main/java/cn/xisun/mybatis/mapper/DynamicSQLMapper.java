package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;

import java.util.List;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/7/24 21:24
 * @description
 */
public interface DynamicSQLMapper {
    /**
     * 根据多条件查询，可能获得多个结果
     *
     * @param employee 查询条件可能包含多个，以一个Employee对象为参数模拟
     * @return 员工列表
     */
    List<Employee> getEmployeesByConditions(Employee employee);

    /**
     * 根据员工id，批量删除员工
     *
     * @param ids 员工id的数组
     * @return 受影响的条数
     */
    Integer deleteEmployeesByArray(Integer[] ids);
}
