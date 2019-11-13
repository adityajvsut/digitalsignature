package com.ut.digitalsignature.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // try {
        //     if (httpRequest.getHeader("Authorization").equals(null)) {
        //         throw new Exception();
        //     }
        //     final String baseUrl = "http://localhost:9015/digisign/authentication";
        //     RestTemplate restTemplate = new RestTemplate();
        //     String token = httpRequest.getHeader("Authorization");
        //     ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, token, String.class);
        //             if(responseEntity.getBody().equals("Failed")){throw new Exception();};     
        //         }
        //         catch(Exception e){
        //             httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        //             httpResponse.getWriter().write("Error");
        //             httpResponse.flushBuffer();
        //             return;
        //         }

                  chain.doFilter(request, response);

    }
        
}
