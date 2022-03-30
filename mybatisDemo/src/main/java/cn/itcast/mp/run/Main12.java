package cn.itcast.mp.run;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main12 {
    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(is);
        SqlSession sess = factory.openSession(true);
        UserMapper mapper = sess.getMapper(UserMapper.class);
        QueryWrapper<User> wrapper = new QueryWrapper();
        Page<User> p = new Page(3,2);
        Page<User> page = mapper.selectPage(p, wrapper);
        long pageCount = page.getPages();//一共可以分多少页
        long pageNum = page.getCurrent();//当前是第几页
        long size = page.getSize();//一页有多少条数据
        long total = page.getTotal();//数据总数量
        List<User> records = page.getRecords();
        sess.close();
        is.close();

    }
}
