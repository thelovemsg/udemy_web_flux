package com.vinsguru.userservice.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceR2DBCConfig extends AbstractR2dbcConfiguration {
    @Nonnull
    @Override
    @Bean("customR2ConnectionFactory")
    @Primary
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:mysql://root:1234@localhost:3306/world?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul");
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}

