package com.project.psicoproject1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.psicoproject1.model.Cliente;
import com.project.psicoproject1.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long>{

		
}
