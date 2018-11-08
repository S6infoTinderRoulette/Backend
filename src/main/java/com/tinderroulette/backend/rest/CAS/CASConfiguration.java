package com.tinderroulette.backend.rest.CAS;

import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class CASConfiguration {
    private static final String SERVICE = "http://localhost:8000/login/cas";
    private static final String LOGIN_URL = "https://cas.usherbrooke.ca/login";
    private static final String TICKET = "https://cas.usherbrooke.ca/";

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(SERVICE);
        serviceProperties.setSendRenew(true);
        return serviceProperties;
    }

    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(LOGIN_URL);
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(TICKET);
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(ticketValidator());
        provider.setUserDetailsService(new UserDetailsServiceImpl());
        provider.setKey("TEST");
        return provider;
    }
}