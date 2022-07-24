package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/17 21:33
 * @description
 */
class SelectMapperTest {

    @Test
    void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SelectMapper selectMapper = sqlSession.getMapper(SelectMapper.class);
//        User user = selectMapper.getUserById(3);
//        List<User> allUser = selectMapper.getAllUser();
//        Integer total = selectMapper.getTotal();
//        Map<String, Object> user = selectMapper.getUserToMap(3);
//        List<Map<String, Object>> allUser = selectMapper.getAllUserToMap();
        Map<String, Object> allUser = selectMapper.getAllUserToMap();
        System.out.println(allUser);
    }
}