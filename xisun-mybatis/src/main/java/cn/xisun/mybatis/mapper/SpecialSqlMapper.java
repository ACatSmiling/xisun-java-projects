package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/18 0:44
 * @description
 */
public interface SpecialSqlMapper {
    /**
     * 模糊查询
     *
     * @param fuzzy 模糊查询条件
     * @return 用户列表
     */
    List<User> fuzzyQuery(@Param("fuzzy") String fuzzy);

    /**
     * 批量删除
     *
     * @param ids 待删除的用户的id
     * @return 受影响的条数
     */
    int deleteMore(@Param("ids") String ids);

    /**
     * 动态设置表名，查询所有的用户信息
     *
     * @param tableName 表名
     * @return 返回表中的所有信息
     */
    List<User> getAllUser(@Param("tableName") String tableName);

    /**
     * 添加用户
     *
     * @param user 用户实例
     * @return 受影响的条数
     */
    int insertUser(User user);
}
