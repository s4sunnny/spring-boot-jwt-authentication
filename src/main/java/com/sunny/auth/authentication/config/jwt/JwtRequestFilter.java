//package com.sunny.auth.authentication.config.jwt;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private UserDetailsServiceImpl jwtUserDetailsService;
//
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//
//		String requestTokenHeader = null;
//
//		requestTokenHeader = request.getHeader("Authorization");
//		// requestTokenHeader = requestTokenHeader.split(" ")[1];
//
//		String username = null;
//		String jwtToken = null;
//
//		if (requestTokenHeader != null) {
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//			} catch (IllegalArgumentException e) {
//				System.out.println("Unable to get JWT Token");
//			} catch (ExpiredJwtException e) {
//				System.out.println("JWT Token has expired");
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				ResponseUtil responseUtil = new ResponseUtil();
//				responseUtil.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
//				responseUtil.setMessage("JWT Token has expired");
//				byte[] resBytes = new ObjectMapper().writeValueAsBytes(responseUtil);
//				response.getOutputStream().write(resBytes);
//				response.getOutputStream().flush();
//				response.getOutputStream().close();
//				return;
//			}
//		}
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			UserSession userDetails = (UserSession) this.jwtUserDetailsService.loadUserByUsername(username);
//			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
////				if(!userDetails.getToken().equals(jwtToken)) {
////					throw new ApplicationException("Invalid session!");
////				}
//				// System.out.println("validateToken");
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken
//						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
//		}
//		chain.doFilter(request, response);
//	}
//
//}
