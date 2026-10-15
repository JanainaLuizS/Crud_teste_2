package br.com.senai.teste.config;


import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth-> auth
            .requestMatchers(HttpMethod.POST,"/usuarios").permitAll()
            .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    
    @Bean 
    public JwtEncoder jwtEncoder(SecretKey secretKey){
        OctetSequenceKey jwtKey = new OctetSequenceKey.Builder(secretKey).algorithm(JWSAlgorithm.HS256).build();

        JWKSource<SecurityContext>jwkSource = new ImmutableJWKSet<>(new JWKSet(jwtKey));

        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder (SecretKey secretKey){

        NimbusJwtDecoder decoder = NimbusJwtDecoder
        .withSecretKey(secretKey)
        .macAlgorithm(MacAlgorithm.HS256)
        .build();

        OAuth2TokenValidator<Jwt>validador = JwtValidators.createDefaultWithIssuer("teste-api");

        decoder.setJwtValidator(validador);

        return decoder;
    }

    @Bean
    public SecretKey jwtSecretKey(
        @Value("${jwt,secret}")String secret){

           byte[]chave = secret.getBytes(StandardCharsets.UTF_8);
           
           if (chave.length <32){
            throw new  IllegalArgumentException("A chave JWT deve possuir pelo menos 32 caracteres")
           }

           return  new SecretKeySpec(chave, "HmacSHA256");
        }

        @Bean 
        public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception{

            return  configuration.getAuthenticationManager();}
        
    
}
