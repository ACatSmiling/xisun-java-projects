package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/25 14:06
 * @description
 */
class CacheMapperTest {

    @Test
    void test1() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper = sqlSession.getMapper(CacheMapper.class);
        Employee employee1 = cacheMapper.getEmployeeById(2);
        System.out.println(employee1);
        // 执行相同地查询，可以看到，这一次查询没有打印SQL语句，说明是从缓存中获取的结果
        Employee employee2 = cacheMapper.getEmployeeById(2);
        System.out.println(employee2);
    }

    @Test
    void test2() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper1 = sqlSession.getMapper(CacheMapper.class);
        Employee employee1 = cacheMapper1.getEmployeeById(2);
        System.out.println(employee1);
        // 使用相同的sqlSession再次创建一个CacheMapper，执行相同的查询，也没有打印SQL语句
        CacheMapper cacheMapper2 = sqlSession.getMapper(CacheMapper.class);
        Employee employee2 = cacheMapper2.getEmployeeById(2);
        System.out.println(employee2);
        // 创建一个新的SqlSession，执行相同的查询，可以看到，这次打印了SQL语句
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper3 = sqlSession2.getMapper(CacheMapper.class);
        Employee employee3 = cacheMapper3.getEmployeeById(2);
        System.out.println(employee3);
    }

    @Test
    void test3() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper = sqlSession.getMapper(CacheMapper.class);
        Employee employee1 = cacheMapper.getEmployeeById(2);
        System.out.println(employee1);
        // 手动清缓存，也可以使一级缓存失效
//        sqlSession.clearCache();
        // 在两次相同的查询之前，执行一次插入操作
        Employee employee = new Employee();
        employee.setEmpName("王二");
        employee.setSex("男");
        employee.setAge(29);
        employee.setDepId(2);
        int i = cacheMapper.insertEmployee(employee);
        System.out.println(i);
        Employee employee2 = cacheMapper.getEmployeeById(2);
        System.out.println(employee2);
    }

    // 测试二级缓存
    @Test
    void test4() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper1 = sqlSession1.getMapper(CacheMapper.class);
        System.out.println(cacheMapper1.getEmployeeById(2));
        // 关闭sqlSession1
        sqlSession1.close();
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper2 = sqlSession2.getMapper(CacheMapper.class);
        System.out.println(cacheMapper2.getEmployeeById(2));
        // 关闭sqlSession2
        sqlSession2.close();
    }
}