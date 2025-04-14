package com.apiLogin.exemplo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.apiLogin.exemplo.controllers.UsuarioController;
import com.apiLogin.exemplo.model.entities.Usuario;
import com.apiLogin.exemplo.model.repositories.UsuarioRepository;

public class ExemploApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testLoginUsuario_Success() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("teste@gmail.com");
        usuario.setSenha("exemplo123");

        when(usuarioRepository.findByEmailOrUsername("teste@gmail.com", "teste@gmail.com"))
                .thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"teste@gmail.com\", \"senha\": \"exemplo123\"}"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    public void testLoginUsuario_Unauthorized() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("teste@gmail.com");
        usuario.setSenha("exemplo123");

        when(usuarioRepository.findByEmailOrUsername("teste@gmail.com", "teste@gmail.com"))
                .thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"teste@gmail.com\", \"senha\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    public void testLoginUsuario_NotFound() throws Exception {
        when(usuarioRepository.findByEmailOrUsername("teste@gmail.com", "teste@gmail.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"teste@gmail.com\", \"senha\": \"exemplo123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    public void testLoginUsuario_ValidationFailed() throws Exception {
        // Aqui você pode adicionar um caso de teste para a validação
        // Se o seu modelo Usuario tiver anotações de validação, você pode simular isso
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"\", \"senha\": \"\"}")) // Exemplo de corpo inválido
                .andExpect(status().isBadRequest());
    }
}
