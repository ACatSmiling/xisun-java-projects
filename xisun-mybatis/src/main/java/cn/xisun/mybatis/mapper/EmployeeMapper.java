package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/20 0:42
 * @description
 */
public interface EmployeeMapper {

    /**
     * 获取所有的员工信息
     *
     * @return 员工列表
     */
    List<Employee> getAllEmployees();

    /**
     * 根据员工id获取员工信息，同时包含员工所处的部门信息
     *
     * @param id 员工id
     * @return 员工实例
     */
    Employee getEmployeeById(@Param("id") Integer id);

    /**
     * 分步查询第一步：获取员工信息
     *
     * @param id 员工id
     * @return 员工实例
     */
    Employee getEmployeeByStepOneById(@Param("id") Integer id);

    /**
     * 分步查询第二步：获取部门信息中的员工信息
     *
     * @param depId 员工所属部门id
     * @return 员工列表
     */
    List<Employee> getEmployeeByStepTwoByDepId(@Param("depId") Integer depId);
}
