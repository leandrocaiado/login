package com.apiLogin.exemplo.model.repositories;

import com.apiLogin.exemplo.model.entities.Produto;
import com.apiLogin.exemplo.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    List<Produto> findAll();
    
    Optional<Produto> findById(Long id);
    
    void deleteById(Long id);
    
    Produto saveAndFlush(Produto produtos);
}
