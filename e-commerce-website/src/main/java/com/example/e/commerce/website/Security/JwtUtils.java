package com.example.e.commerce.website.Security;

import java.util.*;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import com.example.e.commerce.website.dto.Admindto.Login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtUtils {
	
	//65 characters
	private final String SECRET_KEY="aVeryLongSecretKeyThatIsBase64EncodedForSecurityPurpose1234567890";
	public String generateToken(Login use) {
		Map<String,Object> claims=new HashMap<>();
		return Jwts
				.builder()
				.setHeaderParam("alg", "H256")
				.setHeaderParam("typ", "JWT")
				.claims()
				.add(claims)
				.subject(use.getEmail())
				.issuer("Omkar")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+60*10*1000))
				.and()
				.signWith(generateKey())
				.compact();
	}
	public SecretKey generateKey() {
		byte[]decode=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decode);
		
	}
	public String extractUsername(String token) {
		
		return getclaimToken(token,Claims::getSubject);
	}
	private <T>T getclaimToken(String token,Function<Claims,T>claimresolver ) {
		Claims claim=getAllclaimstoken(token);
		return claimresolver.apply(claim);
	}
	private Claims getAllclaimstoken(String token) {
		
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public boolean isValidToken(String token, UserDetails user) {
		String username=extractUsername(token);
		return user.getUsername().equals(username) && !expired(token);
	}
	private boolean expired(String token) {
		final Date expiration=getExpirationToken(token);
		return expiration.before(new Date());
	}
	private Date getExpirationToken(String token) {
		// TODO Auto-generated method stub
		return getclaimToken(token, Claims::getExpiration);
	}

	
}