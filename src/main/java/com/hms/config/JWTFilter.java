package com.hms.config;

//JWTFilter : incoming request with token is valid then accept and give response or not valid then token invalid msg
//custom filter
import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
       

        if(token!=null && token.startsWith("Bearer ")){
            String tokenVal = token.substring(7, token.length());
           
            String userName = jwtService.getUserName(tokenVal);

            Optional<AppUser> opUsername = appUserRepository.findByUsername(userName);
            if(opUsername.isPresent()){
                AppUser appUser = opUsername.get();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(appUser,null,null);
                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth); //grant permission

            }
            

        }
        filterChain.doFilter(request, response); //this line will tell do not send all url here send only the request/url with token (dont send login request which dont have token)

    }
}