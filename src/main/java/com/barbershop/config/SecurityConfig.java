package com.barbershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Указываем, что этот класс содержит конфигурацию для безопасности веб-приложения.
@EnableWebSecurity
public class SecurityConfig {

    // Определяем Bean, который предоставляет сервис для управления данными пользователей.
    @Bean
    public UserDetailsService userDetailsService() {
        // Создаем объект InMemoryUserDetailsManager, который хранит данные пользователей в памяти.
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Создаем пользователя с именем "admin", паролем "1" и ролью "ADMIN".
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin") // Устанавливаем имя пользователя "admin".
                .password("1") // Пароль
                .roles("ADMIN") // Назначаем пользователю роль "ADMIN".
                .build());

        // Возвращаем менеджер пользователей.
        return manager;
    }

    // Определяем Bean для конфигурации цепочки фильтров безопасности.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Настраиваем авторизацию запросов.
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated() // Любой запрос требует аутентификации.
                )
                // Настраиваем форму входа.
                .formLogin(form -> form
                        .loginPage("/login") // Указываем кастомную страницу входа.
                        .permitAll() // Разрешаем доступ ко всем (неаутентифицированным) пользователям.
                )
                // Настраиваем возможность выхода из системы.
                .logout(LogoutConfigurer::permitAll); // Разрешаем выход из системы всем пользователям.

        // Возвращаем собранную цепочку фильтров безопасности.
        return http.build();
    }
}

