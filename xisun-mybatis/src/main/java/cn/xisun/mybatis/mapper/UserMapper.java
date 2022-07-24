package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/14 21:31
 * @description
 */
public interface UserMapper {

    /**
     * 添加用户信息
     *
     * @return 受影响的行数
     */
    int insertUser();

    /**
     * 修改用户信息
     *
     * @return 受影响的行数
     */
    int updateUser();

    /**
     * 删除用户信息
     *
     * @return 受影响的行数
     */
    int deleteUser();

    /**
     * 获取一条用户信息
     *
     * @return 用户实例
     */
    User getUserById();

    /**
     * 获取全部用户信息
     *
     * @return 用户列表
     */
    List<User> getUserList();

    /**
     * 根据用户名获取用户信息
     *
     * @param name 用户名
     * @return 用户实例
     */
    User getUserByName(String name);

    /**
     * 根据用户名和状态获取用户信息
     *
     * @param name   用户名
     * @param status 状态
     * @return 用户实例
     */
    User getUserByNameAndStatus(String name, Integer status);

    /**
     * 将查询参数放到Map中
     *
     * @param map 查询参数的Map
     * @return 用户实例
     */
    User getUserByMap(Map<String, Object> map);

    /**
     * 将User实体添加到数据库
     *
     * @param user User实体
     * @return 受影响的行数
     */
    int insertUser(User user);

    /**
     * 通过@Param注解查询用户
     *
     * @param name   用户名
     * @param status 状态
     * @return 用户实例
     */
    User getUserByParam(@Param("name") String name, @Param("status") Integer status);
}
