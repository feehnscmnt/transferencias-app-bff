package br.com.transferenciasapp.dto;

import br.com.transferenciasapp.entity.TransferenciaEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/**
 * Classe DTO para o tráfego dos dados de saída das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@Data
public class TransferenciaResponseDTO {
	
	public Long id;
	public String contaOrigem;
	public String contaDestino;
	public BigDecimal valor;
	public BigDecimal taxa;
	public BigDecimal valorTotal;
	public LocalDate dataTransferencia;
	public LocalDate dataAgendamento;
	
	public TransferenciaResponseDTO(TransferenciaEntity transferenciaEntity) {
		id = transferenciaEntity.getId();
		contaOrigem = transferenciaEntity.getContaOrigem();
		contaDestino = transferenciaEntity.getContaDestino();
		valor = transferenciaEntity.getValor();
		taxa = transferenciaEntity.getTaxa();
		valorTotal = transferenciaEntity.getValor().add(transferenciaEntity.getTaxa());
		dataTransferencia = transferenciaEntity.getDataTransferencia();
		dataAgendamento = transferenciaEntity.getDataAgendamento();
	}
	
}