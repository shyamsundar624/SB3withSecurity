package com.library.jwt;

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

import com.library.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
@Autowired
	private  JWTService jwtService;
@Autowired
private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader=request.getHeader("Authorization");
		final String jwtToken;
		final String username;

		// check if authorization header is present & start with bearer

		if(authHeader==null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extract JWT Token from header

		jwtToken=authHeader.substring(7);
		username=jwtService.extractUsername(jwtToken);
	
		// check if we have a username and no authentication exist yet
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Get the user detail from DB
			var userDetails = userRepository.findByusername(username)
					.orElseThrow(() -> new RuntimeException("User Not Found"));
			// validate the token
			if (jwtService.isTokenValid(jwtToken, userDetails)) {
				// create the authentication with user roles
				List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, authorities);

//Set authentication Details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				//update Security context with authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		}
		filterChain.doFilter(request, response);
	}

}
