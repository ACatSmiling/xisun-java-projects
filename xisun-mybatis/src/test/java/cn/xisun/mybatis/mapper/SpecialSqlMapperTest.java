package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/18 0:52
 * @description
 */
class SpecialSqlMapperTest {

    @Test
    void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SpecialSqlMapper specialSqlMapper = sqlSession.getMapper(SpecialSqlMapper.class);
//        List<User> users = specialSqlMapper.fuzzyQuery("张三");
//        System.out.println(users);
//        int i = specialSqlMapper.deleteMore("2, 3, 4");
//        System.out.println(i);
//        List<User> users = specialSqlMapper.getAllUser("mybatis.`user` u");
//        System.out.println(users);
        User user = new User();
        user.setName("王五");
        user.setSex("男");
        user.setAge(28);
        user.setStatus(1);
        user.setCreate_time(Date.from(Instant.now()));
        user.setUpdate_time(Date.from(Instant.now()));
        System.out.println(user);
        int i = specialSqlMapper.insertUser(user);
        System.out.println(user);
    }

    @Test
    void testJdbc() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.10.100:3306/mybatis",
                "root", "root");
        // Statement.RETURN_GENERATED_KEYS：返回主键
        // Statement.NO_GENERATED_KEYS：不返回主键
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO mybatis.`user` (name, sex, age, status) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        User user = new User();
        user.setName("王五");
        user.setSex("男");
        user.setAge(28);
        user.setStatus(1);
        ps.setString(1, user.getName());
        ps.setString(2, user.getSex());
        ps.setInt(3, user.getAge());
        ps.setInt(4, user.getStatus());
        ps.executeUpdate();
        ResultSet resultSet = ps.getGeneratedKeys();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            System.out.println(id);
        }

    }
}