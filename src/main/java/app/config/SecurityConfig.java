package app.config;

import app.util.TokenHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@PropertySource(value = "classpath:application.properties")
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenHeaderFilter filter = new TokenHeaderFilter(environment.getRequiredProperty("security.headerName"));

        filter.setAuthenticationManager(authentication -> {

            String principal = (String) authentication.getPrincipal();
            if (!environment.getRequiredProperty("security.headerValue").equals(principal)) {
                throw new BadCredentialsException("key or value is invalid");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });
        http.antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(filter)
                .addFilterBefore(new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()), filter.getClass())
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
