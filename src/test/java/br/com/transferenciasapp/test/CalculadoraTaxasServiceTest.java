package br.com.transferenciasapp.test;

import br.com.transferenciasapp.service.CalculadoraTaxasService;
import br.com.transferenciasapp.exception.RegraNegocioException;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe test responsável pelo teste unitário das taxas de transferência.
 * 
 * @author Felipe Nascimento
 * 
 */

public class CalculadoraTaxasServiceTest {
	
	private CalculadoraTaxasService calculadoraTaxasService = new CalculadoraTaxasService();
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 no mesmo dia.
	 */
	@Test
	public void calculaMesmoDia() {
		Assert.assertEquals(new BigDecimal("28.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 0));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 em até dez dias.
	 */
	@Test
	public void calculaAteDezDias() {
		Assert.assertEquals(new BigDecimal("12.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 10));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 em até vinte dias.
	 */
	@Test
	public void calculaAteVinteDias() {
		Assert.assertEquals(new BigDecimal("82.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 20));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 em até trinta dias.
	 */
	@Test
	public void calculaAteTrintaDias() {
		Assert.assertEquals(new BigDecimal("69.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 30));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 em até quarenta dias.
	 */
	@Test
	public void calculaAteQuarentaDias() {
		Assert.assertEquals(new BigDecimal("47.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 40));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 em até cinquenta dias.
	 */
	@Test
	public void calculaAteCinquentaDias() {
		Assert.assertEquals(new BigDecimal("17.00"), calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 50));
	}
	
	/**
	 * Cenário de teste - transferência de R$1.000,00 acima de cinquenta dias.
	 */
	@Test
	public void bloqueiaAcimaCinquentaDias() {
		Assert.assertThrows(RegraNegocioException.class, () -> calculadoraTaxasService.calcular(new BigDecimal("1000.00"), 51));
	}
	
}