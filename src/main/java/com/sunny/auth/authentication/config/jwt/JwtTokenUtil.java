package com.sunny.auth.authentication.config.jwt;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${jwt.secret.key}")
	private String key;

	@Value("${jwt.secret.algorithm}")
	private String algorithm;

	@Value("${jwt.secret.issuer}")
	private String issuer;

	@Value("${jwt.secret.expiresIn}")
	private String expiresIn;

	public String generateJWT(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("scope", userDetails.getAuthorities());

		var header = new JWSHeader(JWSAlgorithm.parse(algorithm));
		var claimsSet = buildClaimsSet(claims);

		var jwt = new SignedJWT(header, claimsSet);

		try {
			var signer = new MACSigner(key);
			jwt.sign(signer);
		} catch (JOSEException e) {
			throw new RuntimeException("Unable to generate JWT", e);
		}

		return jwt.serialize();
	}

	private JWTClaimsSet buildClaimsSet(Map<String, Object> claims) {
		var issuedAt = Instant.now();
		var expirationTime = issuedAt.plus(Duration.parse(expiresIn));

		var builder = new JWTClaimsSet.Builder().issuer(issuer).issueTime(Date.from(issuedAt))
				.expirationTime(Date.from(expirationTime));

		claims.forEach(builder::claim);

		return builder.build();
	}

//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	public Date getIssuedAtDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getIssuedAt);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//	}

//	private Boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}
//
//	private Boolean ignoreTokenExpiration(String token) {
//		// here you specify tokens, for that the expiration is ignored
//		return false;
//	}

//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return doGenerateToken(claims, userDetails.getUsername());
//	}

//	private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
//	}

//	public Boolean canTokenBeRefreshed(String token) {
//		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//	}
//
//	public String expireCurrentToken(String token) {
//		// final Date expirationDate = getExpirationDateFromToken(token);
//
//		final Claims claims = getAllClaimsFromToken(token);
//		claims.setExpiration(new Date());
//		claims.setSubject(getUsernameFromToken(token));
//		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
//	}
//
//	public Boolean validateToken(String token, UserDetails userDetails) {
//		final String username = getUsernameFromToken(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
}
