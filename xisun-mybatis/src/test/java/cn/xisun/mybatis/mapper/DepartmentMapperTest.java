package cn.xisun.mybatis.mapper;

import cn.xisun.mybatis.entity.Department;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/7/24 9:34
 * @description
 */
class DepartmentMapperTest {

    @Test
    void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
//        Department department = departmentMapper.getDepartmentById(2);
//        System.out.println(department);
        Department department = departmentMapper.getDepartmentByStepOneById(2);
        System.out.println(department.getDepName());
        System.out.println("--------------------------------------------");
        System.out.println(department.getEmployees());
    }
}