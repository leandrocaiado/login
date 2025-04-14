package com.apiLogin.exemplo.security;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JWTUtil {
	  public String generateToken(String nome) {
	        try {
	        	 String JWT_TOKEN_KEY = "supersecret";
	            Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
	            Date expirationDate = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
	            Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
	            return JWT.create()
	                    .withIssuedAt(issuedAt) // Issue date.
	                    .withExpiresAt(expirationDate) // Expiration date.
	                    .withClaim("userId", nome) // User id - here we can put anything we want, but for the example userId is appropriate.
	                    .withIssuer("jwtauth") // Issuer of the token.
	                    .sign(algorithm); // And the signing algorithm.
	        } catch (UnsupportedEncodingException | JWTCreationException e) {
	          e.printStackTrace();
	        }
	        return null;
	    }
}
