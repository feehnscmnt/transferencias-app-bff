package br.com.transferenciasapp.entity;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe entity responsável por gerar a tabela de transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@Getter
@Setter
@Entity
@Table(name = "transferencias")
public class TransferenciaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 10)
	private String contaOrigem;
	
	@Column(nullable = false, length = 10)
	private String contaDestino;
	
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal valor;
	
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal taxa;
	
	@Column(nullable = false)
	private LocalDate dataTransferencia;
	
	@Column(nullable = false, updatable = false)
	private LocalDate dataAgendamento;
	
	public TransferenciaEntity(String origem, String destino, BigDecimal valor, BigDecimal taxa, LocalDate transferencia, LocalDate agendamento) {
		this.contaOrigem = origem;
		this.contaDestino = destino;
		this.valor = valor;
		this.taxa = taxa;
		this.dataTransferencia = transferencia;
		this.dataAgendamento = agendamento;
	}
	
	protected TransferenciaEntity() {}
	
}