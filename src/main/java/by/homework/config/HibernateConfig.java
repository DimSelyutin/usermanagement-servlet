package by.homework.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.homework.entity.AdminUser;
import by.homework.entity.CommonUser;
import by.homework.entity.GuestUser;
import by.homework.entity.Role;
import by.homework.entity.User;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        try {
            return new org.hibernate.cfg.Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Role.class)
                    .addAnnotatedClass(AdminUser.class)
                    .addAnnotatedClass(CommonUser.class)
                    .addAnnotatedClass(GuestUser.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}