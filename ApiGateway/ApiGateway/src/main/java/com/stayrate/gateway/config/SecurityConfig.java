package com.stayrate.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
		httpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated()).oauth2Client(oauth2 -> {
		}).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
		}));

		return httpSecurity.build();
	}
}