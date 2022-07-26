package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/7/24 21:33
 * @description
 */
class DynamicSQLMapperTest {

    @Test
    void getEmployeesByConditions() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        DynamicSQLMapper dynamicSQLMapper = sqlSession.getMapper(DynamicSQLMapper.class);
        Employee employee = new Employee();
        employee.setEmpName("张三");
        employee.setSex("男");
        employee.setAge(27);
        List<Employee> employees = dynamicSQLMapper.getEmployeesByConditions(employee);
        System.out.println(employees);
    }

    @Test
    void testDelete() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        DynamicSQLMapper dynamicSQLMapper = sqlSession.getMapper(DynamicSQLMapper.class);
        // 待删除员工id
        Integer[] ids = {7, 8, 9};
        Integer i = dynamicSQLMapper.deleteEmployeesByArray(ids);
        System.out.println(i);
    }

    @Test
    void testInsert() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        DynamicSQLMapper dynamicSQLMapper = sqlSession.getMapper(DynamicSQLMapper.class);
        // 待添加员工
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setEmpName("张三");
        employee1.setSex("男");
        employee1.setAge(29);
        employee1.setDepId(1);
        employees.add(employee1);
        Employee employee2 = new Employee();
        employee2.setEmpName("张三");
        employee2.setSex("男");
        employee2.setAge(29);
        employee2.setDepId(1);
        employees.add(employee2);
        Employee employee3 = new Employee();
        employee3.setEmpName("张三");
        employee3.setSex("男");
        employee3.setAge(29);
        employee3.setDepId(1);
        employees.add(employee3);
        Integer i = dynamicSQLMapper.insertEmployeesByList(employees);
        System.out.println(i);
    }

    @Test
    void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        DynamicSQLMapper dynamicSQLMapper = sqlSession.getMapper(DynamicSQLMapper.class);
        Employee employee = dynamicSQLMapper.getEmployeeById(2);
        System.out.println(employee);
    }
}