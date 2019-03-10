package cc.mrbird.febs;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class FebsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsApplication.class, args);
    }
}
