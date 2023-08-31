
package com.mjc.school.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EntityScan(basePackages = "com.mjc.school.repository.model")
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RepoConfig {
    public static void main(String[] args) {

        SpringApplication.run(RepoConfig.class, args);

    }

    /*@Bean
    public CommandLineRunner CommandLineRunnerBean(DBLoader db) {
        return (args) -> {
            db.writeNews();
        };
    }*/

}
