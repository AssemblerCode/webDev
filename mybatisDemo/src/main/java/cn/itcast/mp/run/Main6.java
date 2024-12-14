package cn.itcast.mp.run;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main6 {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(is);
        SqlSession sess = factory.openSession(true);
        UserMapper mapper = sess.getMapper(UserMapper.class);
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("id", "123");
        wrapper.eq("name", "123");
//        id=123 and name=123
        mapper.delete(wrapper);
        sess.close();
        is.close();

    }
}
