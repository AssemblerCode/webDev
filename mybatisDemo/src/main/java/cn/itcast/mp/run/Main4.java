package cn.itcast.mp.run;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

public class Main4 {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(is);
        SqlSession sess = factory.openSession(true);
        UserMapper mapper = sess.getMapper(UserMapper.class);
        User u = new User();
        u.setAge(30);
        u.setEmail("cc@qq.com");
        u.setPwd("123456789");
        u.setUserName("曹操");
        UpdateWrapper<User> wrapper = new UpdateWrapper();
        wrapper.eq("age","40");
        int count = mapper.update( u,wrapper);

        QueryWrapper<User> query = new QueryWrapper();
        query.eq("age","30");
        u.setAge(40);
          count = mapper.update(u, query);

        sess.close();
        is.close();

    }
}
