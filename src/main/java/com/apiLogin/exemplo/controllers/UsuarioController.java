package com.apiLogin.exemplo.controllers;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apiLogin.exemplo.model.entities.Usuario;
import com.apiLogin.exemplo.model.repositories.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping(value = "/login")
	public ResponseEntity loginUsuario(@RequestBody @Valid Usuario usuarioRequest, BindingResult bindingResult) {

//        EXEMPLO REQUEST USUARIO
//        {
//            "username": "teste@gmail.com",
//                "senha": "exemplo123"
//        }

		try {

			if (bindingResult.hasErrors()) {
				return ResponseEntity.badRequest().body("Validation failed");
			}
			Optional<Usuario> usuario = usuarioRepository.findByEmailOrUsername(usuarioRequest.getUsername(),
					usuarioRequest.getUsername());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("token", generateToken(usuarioRequest.getUsername()));
			if (usuario.isPresent()) {

				if (usuario.get().equals(usuarioRequest)) {
					return ResponseEntity.ok().headers(headers).build();
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
				}

			} else {
				return ResponseEntity.notFound().headers(headers).build();
			}

		} catch (Exception e) {
			throw e;
		}

	}
	
	   private String generateToken(String nome) {
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
