package cn.itcast.mp.util;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

@Data
public class GetSqlSession {
    private static SqlSessionFactory builder;

    static {
        if (builder == null) {
            InputStream is = null;
            is = GetSqlSession.class.getResourceAsStream("/SqlMapperConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            GetSqlSession.builder = builder.build(is);

        }
    }

    public static SqlSession getSession() {
        SqlSession sess = builder.openSession();
        return sess;
    }
}
