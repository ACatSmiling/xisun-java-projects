package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Department;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/20 0:43
 * @description
 */
public interface DepartmentMapper {
    /**
     * 分步查询第二步：获取员工信息中的部门信息
     *
     * @param id 部门id
     * @return 部门实例
     */
    Department getDepartmentByStepTwoById(@Param("id") Integer id);

    /**
     * 获取部门信息，包含部门内的所有员工
     *
     * @param id 部门id
     * @return 部门实例
     */
    Department getDepartmentById(@Param("id") Integer id);
}
