package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/14 21:50
 * @description
 */
class UserMapperTest {

    @Test
    void test() throws IOException {
        // 读取MyBatis的核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        // 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 通过核心配置文件所对应的字节输入流创建工厂类SqlSessionFactory，生产SqlSession对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

        // 创建SqlSession对象，此时通过SqlSession对象所操作的sql都必须手动提交或回滚事务
        // SqlSession sqlSession = sqlSessionFactory.openSession();
        // 在执行相应逻辑代码后，需要手动提交事务
        // sqlSession.commit();

        // 创建SqlSession对象，此时通过SqlSession对象所操作的sql都会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        // 通过代理模式创建UserMapper接口的代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 调用UserMapper接口中的方法，就可以根据UserMapper的全类名匹配元素文件，通过调用的方法名匹配
        // 映射文件中的SQL标签，并执行标签中的SQL语句
//        int result = userMapper.insertUser();

//        int result = userMapper.updateUser();

//        int result = userMapper.deleteUser();

//        User user = userMapper.getUserById();
//
//        List<User> users = userMapper.getUserList();

//        User user = userMapper.getUserByName("张三");

//        User user = userMapper.getUserByNameAndStatus("张三", 1);

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "张三");
//        map.put("status", "1");
//        User user = userMapper.getUserByMap(map);

//        User user = new User();
//        user.setName("王五");
//        user.setSex("男");
//        user.setAge(28);
//        user.setStatus(1);
//        user.setCreate_time(Date.from(Instant.now()));
//        user.setUpdate_time(Date.from(Instant.now()));
//        System.out.println(user);
//        int i = userMapper.insertUser(user);
//        System.out.println(i);

        User user = userMapper.getUserByParam("张三", 1);
        System.out.println(user);
    }
}