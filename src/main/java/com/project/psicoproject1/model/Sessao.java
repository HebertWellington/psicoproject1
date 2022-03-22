package com.project.psicoproject1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sessoes")
public class Sessao {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 10)
    private Integer quantidadeSessao;

    @NotBlank
    @Size(max = 45)
    private Date dataPagamento;

    @NotBlank
    @Size(max = 40)
    private String tipoPagamento;

    @NotBlank
    @Size(max = 10)
    private Double precoSessao;
    
    @NotBlank
    @Size(max = 45)
    private Date dataSessao;
       
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientes_id", nullable = false)
    private Cliente clientes;

   
	public Sessao() {

    }


	public Sessao(Integer quantidadeSessao, Date dataPagamento, String tipoPagamento, Double precoSessao, Date dataSessao, Cliente clientes) {
		
		this.quantidadeSessao = quantidadeSessao;
		this.dataPagamento = dataPagamento;
		this.tipoPagamento = tipoPagamento;
		this.precoSessao = precoSessao;
		this.dataSessao = dataSessao;
		this.clientes = clientes;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getQuantidadeSessao() {
		return quantidadeSessao;
	}


	public void setQuantidadeSessao(Integer quantidadeSessao) {
		this.quantidadeSessao = quantidadeSessao;
	}


	public Date getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


	public String getTipoPagamento() {
		return tipoPagamento;
	}


	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}


	public Double getPrecoSessao() {
		return precoSessao;
	}


	public void setPrecoSessao(Double precoSessao) {
		this.precoSessao = precoSessao;
	}


	public Date getDataSessao() {
		return dataSessao;
	}


	public void setDataSessao(Date dataSessao) {
		this.dataSessao = dataSessao;
	}


	public Cliente getClientes() {
		return clientes;
	}


	public void setClientes(Cliente clientes) {
		this.clientes = clientes;
	}


	

}
