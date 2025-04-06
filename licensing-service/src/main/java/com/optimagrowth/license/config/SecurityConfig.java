package com.optimagrowth.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private final OstockProperties ostockProperties;

    public SecurityConfig(OstockProperties ostockProperties) {
        this.ostockProperties = ostockProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .oauth2ResourceServer(
                    oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(this::convertJwtToAuthorities);
        return jwtAuthConverter;
    }

    private Collection<GrantedAuthority> convertJwtToAuthorities(Jwt jwt) {
        return Stream.concat(extractRolesFromRealmAccess(jwt).stream(), extractRolesFromResourceAccess(jwt).stream())
            .toList();
    }

    private List<GrantedAuthority> extractRolesFromRealmAccess(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        return getGrantedAuthorities(realmAccess);
    }

    @SuppressWarnings("unchecked")
    private List<GrantedAuthority> extractRolesFromResourceAccess(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess == null) {
            return Collections.emptyList();
        }
        Map<String, Object> client = (Map<String, Object>) resourceAccess.get(ostockProperties.getClientName());
        return getGrantedAuthorities(client);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Map<String, Object> client) {
        if (client == null) {
            return Collections.emptyList();
        }
        List<String> roles = safeCastToListOfStrings(client.get("roles"));
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream().map(this::formatRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String formatRole(String role) {
        return "ROLE_" + role;
    }

    @Bean
    public AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> Stream.concat(extractRolesFromClaims(claims), extractRolesFromClaimsResource(claims)).toList();
    }

    @SuppressWarnings("unchecked")
    private Stream<GrantedAuthority> extractRolesFromClaims(Map<String, Object> claims) {
        Map<String, Object> section = (Map<String, Object>) claims.get("realm_access");
        return getGrantedAuthorityStream(section);
    }

    @SuppressWarnings("unchecked")
    private Stream<GrantedAuthority> extractRolesFromClaimsResource(Map<String, Object> claims) {
        Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
        if (resourceAccess == null) {
            return Stream.empty();
        }
        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(ostockProperties.getClientName());
        return getGrantedAuthorityStream(resource);
    }

    private Stream<GrantedAuthority> getGrantedAuthorityStream(Map<String, Object> resource) {
        if (resource == null) {
            return Stream.empty();
        }
        List<String> roles = safeCastToListOfStrings(resource.get("roles"));
        return roles.stream().map(this::formatRole).map(SimpleGrantedAuthority::new);
    }

    @Bean
    GrantedAuthoritiesMapper authenticationConverter(
            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
        return authorities -> authorities.stream()
            .filter(Objects::nonNull)
            .map(OidcUserAuthority.class::cast)
            .map(OidcUserAuthority::getIdToken)
            .map(OidcIdToken::getClaims)
            .map(authoritiesConverter::convert)
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    private List<String> safeCastToListOfStrings(Object obj) {
        if (obj instanceof List<?> list) {
            return list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        }
        return List.of();
    }

}