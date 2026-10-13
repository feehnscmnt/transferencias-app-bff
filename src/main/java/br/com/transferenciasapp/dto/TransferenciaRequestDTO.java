package br.com.transferenciasapp.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/**
 * Classe DTO para o tráfego dos dados de entrada das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@Data
@RequiredArgsConstructor
public class TransferenciaRequestDTO {
	
	@NotBlank
	@Pattern(regexp = "\\d{10}", message = "A conta de origem deve possuir 10 dígitos.")
	private final String contaOrigem;
	
	@NotBlank
	@Pattern(regexp = "\\d{10}", message = "A conta de destino deve possuir 10 dígitos.")
	private final String contaDestino;
	
	@NotNull
	@DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
	private final BigDecimal valor;
	
	@NotNull
	@FutureOrPresent(message = "A data da transferência não pode ser anterior a hoje.")
	private final LocalDate dataTransferencia;
	
}