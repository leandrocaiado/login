package com.apiLogin.exemplo.controllers;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apiLogin.exemplo.model.entities.Produto;
import com.apiLogin.exemplo.model.entities.Usuario;
import com.apiLogin.exemplo.model.repositories.ProdutoRepository;
import com.apiLogin.exemplo.model.repositories.UsuarioRepository;
import com.apiLogin.exemplo.security.JWTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ProdutoRepository produtoRepository;

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
			headers.add("token", new JWTUtil().generateToken(usuarioRequest.getUsername()));
			if (usuario.isPresent()) {
				headers.add("super",usuario.get().getSuperUsuario());
				
				
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
	
	
	
	@PostMapping(value = "/consultaProduto")
	public ResponseEntity consultaProduto(@RequestBody @Valid Usuario usuarioRequest, BindingResult bindingResult) {

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
			headers.add("token", new JWTUtil().generateToken(usuarioRequest.getUsername()));
			
			if (usuario.isPresent()) {

				if (usuario.get().equals(usuarioRequest)) {
					
					
					
					List<Produto> listaProduto = produtoRepository.findAll(); 
					Gson gson = new Gson();
			        String json = gson.toJson(listaProduto);

					headers.add("listaProduto", json);
					
					
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
	
	

	@PostMapping(value = "/deletarProduto")
	public ResponseEntity deletarProduto(@RequestBody @Valid Usuario usuarioRequest, BindingResult bindingResult) {

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
			headers.add("token", new JWTUtil().generateToken(usuarioRequest.getUsername()));
			
			if (usuario.isPresent()) {

				if (usuario.get().equals(usuarioRequest)) {
					
					
					
					produtoRepository.deleteById(usuarioRequest.getId()); 
					
					
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
	
	
	

	@PostMapping(value = "/editarProduto")
	public ResponseEntity editarProduto(@RequestBody @Valid Produto usuarioRequest, BindingResult bindingResult) {

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
			headers.add("token", new JWTUtil().generateToken(usuarioRequest.getUsername()));
			
			if (usuario.isPresent()) {

				if (usuario.get().getEmail().equals(usuarioRequest.getUsername())) {				
					
					produtoRepository.save(usuarioRequest) ;
					
					
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
}
