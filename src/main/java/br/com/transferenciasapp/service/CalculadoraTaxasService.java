package br.com.transferenciasapp.service;

import br.com.transferenciasapp.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.math.BigDecimal;

/**
 * Classe service responsável pelo cálculo de taxas das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@Service
public class CalculadoraTaxasService {
	
	/**
	 * Método responsável pelo cálculo das taxas.
	 * 
	 * @param valor - valor da transferência
	 * @param dias - qtde de dias
	 * 
	 * @return cálculo efetuado
	 * 
	 */
	public BigDecimal calcular(BigDecimal valor, long dias) {
		
		if (dias < 0) throw new RegraNegocioException("A data de transferência não pode estar no passado.");
		
		BigDecimal taxa;
		
		if (dias == 0) taxa = new BigDecimal("3.00").add(percentual(valor, "0.025"));
		else if (dias <= 10) taxa = new BigDecimal("12.00");
		else if (dias <= 20) taxa = percentual(valor, "0.082");
		else if (dias <= 30) taxa = percentual(valor, "0.069");
		else if (dias <= 40) taxa = percentual(valor, "0.047");
		else if (dias <= 50) taxa = percentual(valor, "0.017");
		else throw new RegraNegocioException("Não existe taxa aplicável para transferências acima de 50 dias.");
		
		return taxa.setScale(2, RoundingMode.HALF_UP);
		
	}
	
	/**
	 * Método responsável pelo cálculo do percentual.
	 * 
	 * @param valor - valor da transferência
	 * @param percentual - percentual de cálculo
	 * 
	 * @return cálculo efetuado
	 * 
	 */
	private BigDecimal percentual(BigDecimal valor, String percentual) {
		return valor.multiply(new BigDecimal(percentual));
	}
	
}