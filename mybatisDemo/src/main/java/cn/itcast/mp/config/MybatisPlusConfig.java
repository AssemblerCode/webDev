package cn.itcast.mp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

public class MybatisPlusConfig implements Interceptor {

    @Override
    public Object intercept(Invocation iv) throws Throwable {
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        return pageInterceptor;
    }
}
