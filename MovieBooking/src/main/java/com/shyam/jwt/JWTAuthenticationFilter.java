package com.shyam.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shyam.service.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{



	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader=request.getHeader("Authorization");
		final String jwtToken;
		final String username;
		
		if(authHeader==null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//Extract jwt Token from Header
		
		jwtToken=authHeader.substring(7);
		username=jwtService.extractUsername(jwtToken);
	
		//check if we have a username and no authentication exist yet
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			var userDetails=userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User Not Found"));
			
		//validate Token
			if(jwtService.isTokenValid(jwtToken,userDetails)) {
				//create the authentication with user roles
				List<SimpleGrantedAuthority> authorities=userDetails.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null, authorities);
		//set Authentication Details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// update the security context with authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
filterChain.doFilter(request, response);		
	}

	


}
