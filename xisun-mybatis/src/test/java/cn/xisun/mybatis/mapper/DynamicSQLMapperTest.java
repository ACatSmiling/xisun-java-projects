package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
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
}