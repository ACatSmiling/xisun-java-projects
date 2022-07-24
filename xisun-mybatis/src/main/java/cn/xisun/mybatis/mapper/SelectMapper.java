package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/17 21:27
 * @description
 */
public interface SelectMapper {
    /**
     * 根据用户id查询用户信息
     *
     * @param id 用户id
     * @return 用户实例
     */
    User getUserById(@Param("id") Integer id);

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    List<User> getAllUser();

    /**
     * 查询用户总数
     *
     * @return 用户总数
     */
    Integer getTotal();

    /**
     * 根据用户id查询用户信息，结果转为Map集合
     *
     * @param id 用户id
     * @return 用户实例
     */
    Map<String, Object> getUserToMap(@Param("id") int id);

    /**
     * 方式一：获取所有用户，结果转为Map集合
     * 查询出来的每一条数据都对应一个Map，因此，多条数据，可以放到List集合中
     *
     * @return 用户列表
     */
//    List<Map<String, Object>> getAllUserToMap();

    /**
     * 方式二：获取所有用户，结果转为Map集合
     * 通过@MapKey注解设置Map集合的键，值是该键对应的数据的Map集合
     * 此处以用户id作为键，，最终结果是一个大Map
     *
     * @return 用户列表
     */
    @MapKey("id")
    Map<String, Object> getAllUserToMap();
}
