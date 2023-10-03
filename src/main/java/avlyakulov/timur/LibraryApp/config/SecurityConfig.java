package avlyakulov.timur.LibraryApp.config;

import avlyakulov.timur.LibraryApp.security_service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;

    //настраиваем сам spring security, то какую форму использовать
    //какая страничка за вход, какая за выход, конфигурируем авторизацию
    protected void configure(HttpSecurity http) throws Exception {
        //атворизация
        http.authorizeRequests()
                //не надо писать полное название роли, та как Spring Security все понимает и без нас. Он сам добавляет ROLE_ + наша роль ADMIN/USER
                //.antMatchers("/admin").hasRole("ADMIN")
                //все имеют доступ к этой странице чтоб пройти систему логина
                .antMatchers("/auth/login", "/error", "/auth/registration").permitAll()
                //но для всех других запросов пользователь должен быть аутентифицирован
                .antMatchers("/librarian/**").hasAnyRole("LIBRARIAN", "ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "LIBRARIAN", "ADMIN")
                .anyRequest().authenticated()
                .and()
                //аутентификация
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("users/books")
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    //настраивает аутентификацию проверку логина и пароля
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                //теперь Spring Security автоматически пароль из формы будет прогонять через BCryptPasswordEncoder
                .passwordEncoder(getPasswordEncoder());//Здесь указываем auth provider который мы реализуем. Позже мы указываем сервис
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}