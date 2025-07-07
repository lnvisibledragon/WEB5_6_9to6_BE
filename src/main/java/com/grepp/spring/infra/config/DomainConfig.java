package com.grepp.spring.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.grepp.studium")
@EnableJpaRepositories("com.grepp.spring")
@EnableTransactionManagement
public class DomainConfig {
}
