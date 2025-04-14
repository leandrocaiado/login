package com.apiLogin.exemplo.model.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Produto {
	


    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private String username;
    @Transient
    private String senha;
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long") 
    private String nome;
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 32 characters long") 
    private String descricao;
    private int preco=0;
    private String tipo="";
    private int quantidade=0;
    private String ativo="";
    
    
	public String getSenha() {
		return senha;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getPreco() {
		return preco;
	}
	public void setPreco(int preco) {
		this.preco = preco;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


}
