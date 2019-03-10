package cc.mrbird.febs.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuliuzx
 *
 */
@Configuration
@MapperScan(value={"cc.mrbird.febs.*.dao"})
public class MybatisPlusConfig {

    /**
         *  分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
   /* @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/
    
   
}
