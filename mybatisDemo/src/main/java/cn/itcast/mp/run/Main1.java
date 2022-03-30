package cn.itcast.mp.run;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import cn.itcast.mp.util.GetSqlSession;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main1 {
    public static void main(String[] args) throws IOException {
        SqlSession session = GetSqlSession.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> all = mapper.getAll();
        System.out.println(all);
        session.close();
    }
}
