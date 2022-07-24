package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Department;
import cn.xisun.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author WangDeSong
 * @version 1.0
 * @date 2022/7/22 12:52
 * @description
 */
public class LazyLoadingTest {
    @Test
    void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = employeeMapper.getEmployeeByStepOneById(2);
        // 只需要employee的empName属性，开启延时加载后，不会对Department表查询
        System.out.println(employee.getEmpName());
        System.out.println("--------------------------------------------");
        // 需要employee的department属性，此时，才会执行对Department表的查询操作
        System.out.println(employee.getDepartment());
    }
}
