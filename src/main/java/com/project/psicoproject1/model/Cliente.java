package com.project.psicoproject1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "clientes")
public class Cliente {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
    @Size(max = 45)
    private String nome;
	
	@NotBlank
    @Size(max = 2)
    private Integer idade;

    @NotBlank
    @Size(max = 45)
    private String profissao;

    
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String telefone;
    
    @NotBlank
    @Size(max = 45)
    private Date dataNascimento;
    
    @NaturalId
    @NotBlank
    @Size(max = 45)
    private String cpf;
    
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    @OneToMany(
            mappedBy = "clientes",
            cascade = CascadeType.ALL
             )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Sessao> sessoes = new ArrayList<>();

	public Cliente() {

    }


	public Cliente(String nome, Integer idade, String profissao, String email, String telefone, Date dataNascimento, String cpf, User usuario) {

		this.nome = nome;
		this.idade = idade;
		this.profissao = profissao;
		this.email = email;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.usuario = usuario;

	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getIdade() {
		return idade;
	}


	public void setIdade(Integer idade) {
		this.idade = idade;
	}


	public String getProfissao() {
		return profissao;
	}


	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public String getCpf() {
		return cpf;
	}


	public User getUsuario() {
		return usuario;
	}


	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}


	public List<Sessao> getSessoes() {
		return sessoes;
	}


	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}
	
	
}
