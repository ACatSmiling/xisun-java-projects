package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/25 14:01
 * @description
 */
public interface CacheMapper {
    /**
     * 根据员工id，查找员工信息
     * 测试一级缓存
     *
     * @param id 员工id
     * @return 员工实例
     */
    Employee getEmployeeById(@Param("id") Integer id);

    /**
     * 插入员工
     * 测试一级缓存
     *
     * @param employee 员工实例
     * @return 受影响的条数
     */
    int insertEmployee(Employee employee);
}
