package org.triber.demo.demo.common;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: MybatisPlusConfig
 * @Description:TODO(mybatis-plus插件配置)
 * @author: wl
 * @date: 2018年10月8日 下午11:58:15
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * @throws
     * @Description: TODO(sql性能分析器)
     * @param: @return
     * @return: PerformanceInterceptor
     */
    @Bean
    @Profile({"dev", "prev", "test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor pi = new PerformanceInterceptor();
        pi.setFormat(true);
        pi.setWriteInLog(true);
        return pi;
    }

    /**
     * @throws
     * @Description: TODO(分页插件)
     * @param: @return
     * @return: PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
