package com.cursos.springsecuritycourse.config.security.filter;

import com.cursos.springsecuritycourse.entity.User;
import com.cursos.springsecuritycourse.repository.UserRepository;
import com.cursos.springsecuritycourse.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1 obtener el header que contiene el jwt
        String authHeader = request.getHeader("Authorization"); //Bearer jwt

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //2 obtener jwt desde header
        String jwt = authHeader.split(" ")[1];
        //3 obtener subject/username desde el jwt
        String username = jwtService.extractUsername(jwt);
        //4 setear un objeto authentication dentro del securityContext
        User user = userRepository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        //5 ejecutar el resto de filtros
        filterChain.doFilter(request, response);

    }
}
