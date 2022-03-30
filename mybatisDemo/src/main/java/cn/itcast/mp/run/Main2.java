package cn.itcast.mp.run;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import cn.itcast.mp.util.GetSqlSession;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(is);
        SqlSession sess = factory.openSession();
        UserMapper mapper = sess.getMapper(UserMapper.class);
        QueryWrapper<User> wrapper = new QueryWrapper();
        List<User> all = mapper.selectList(wrapper);
        System.out.println(all);
        sess.close();
        is.close();

    }
}
