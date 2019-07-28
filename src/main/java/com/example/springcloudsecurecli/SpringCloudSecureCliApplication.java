package com.example.springcloudsecurecli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

@SpringBootApplication
public class SpringCloudSecureCliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSecureCliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting cron job");
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setAccessTokenUri("http://localhost:9000/services/oauth/token");
        resourceDetails.setScope(Arrays.asList("toll_read"));
        resourceDetails.setClientId("pluralsight");
        resourceDetails.setClientSecret("pluralsightsecret");
        resourceDetails.setUsername("admin");
        resourceDetails.setPassword("password");

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);

        String token = restTemplate.getAccessToken().toString();
        System.out.println("Token " + token);

        String result = restTemplate.getForObject("http://localhost:9001/services/tolldata", String.class);
        System.out.println("Result: " + result);
    }
}
