//package com.master.practiceReact.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        // allow H2 console only for authenticated users
//                        .requestMatchers("/h2-console/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
//
//                // REQUIRED for H2 console
//                .csrf(csrf -> csrf.disable())
//                .headers(headers -> headers.frameOptions(frame -> frame.disable()));
//
//        return http.build();
//    }
//}



package com.master.practiceReact.config.security;

import com.master.practiceReact.service.ParentDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final ParentDetailsService parentDetailsService;

    public SecurityConfig(ParentDetailsService parentDetailsService) {
        this.parentDetailsService = parentDetailsService;
    }

    /** Main security filter chain **/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()   // allow H2 console
                        .requestMatchers("/api/auth/**").permitAll()     // allow login/signup
                        .anyRequest().authenticated()                    // protect all other endpoints
                )
                .formLogin(Customizer.withDefaults())  // default form login
                .logout(Customizer.withDefaults())     // default logout
                .csrf(csrf -> csrf.disable())          // disable CSRF for H2 console
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // H2 console support

        return http.build();
    }

    /** Password encoder for storing hashed passwords **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** CORS configuration **/
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React dev server
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // allow cookies or auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /** AuthenticationManager for login endpoint **/
    @Bean
    public AuthenticationManager authenticationManager() {
        // provide ParentDetailsService in constructor
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(parentDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // password encoder is still settable

        return new ProviderManager(provider); // wrap in AuthenticationManager
    }



}
