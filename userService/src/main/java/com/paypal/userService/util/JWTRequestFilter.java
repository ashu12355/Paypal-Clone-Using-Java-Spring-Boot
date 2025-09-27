package com.paypal.userService.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTRequestFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws ServletException , IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            try {
                userName = jwtUtil.extractUserName(jwt);
            } catch (Exception e){
                //logMessage-error
            }
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwtUtil.validateToken(jwt,userName)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userName,null,null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);

            if(jwt == null || jwt.isBlank()){
                filterChain.doFilter(request,response);
                return;
            }
            try {
                userName = jwtUtil.extractUserName(jwt);
                String role = jwtUtil.extractRole(jwt);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userName,null, List.of(new SimpleGrantedAuthority(role)));

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request,response);
            } catch(Exception e){
                //log message
            }
        }
        else {
            filterChain.doFilter(request,response);
            return;
        }
    }
}
