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

public class Main5 {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(is);
        SqlSession sess = factory.openSession(true);
        UserMapper mapper = sess.getMapper(UserMapper.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", "123");//等于关系
        paramMap.put("age", "100");
        mapper.deleteByMap(paramMap);//多个条件用and连接
        sess.close();
        is.close();

    }
}
