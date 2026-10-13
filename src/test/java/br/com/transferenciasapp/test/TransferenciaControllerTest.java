package br.com.transferenciasapp.test;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import br.com.transferenciasapp.controller.TransferenciaController;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.transferenciasapp.service.TransferenciaService;
import br.com.transferenciasapp.dto.TransferenciaRequestDTO;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.runner.RunWith;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Clock;
import org.junit.Test;

/**
 * Classe de teste responsável por validar as requisições HTTP da API de transferências.
 * 
 * @author Felipe Nascimento
 */

@RunWith(SpringRunner.class)
@WebMvcTest(TransferenciaController.class)
public class TransferenciaControllerTest {
	
	@MockBean
    private TransferenciaService transferenciaService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
    private MockMvc mockMvc;
	
	/**
	 * Cenário de teste - contas com nove dígitos
	 */
	@Test
    public void contaComNoveDigitos() throws Exception {
		
        var transferenciaRequestDTO = new TransferenciaRequestDTO("123456789", "987654321", new BigDecimal("1000.00"), LocalDate.now(Clock.systemDefaultZone()));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transferencias-app-bff/agendar")
        	.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferenciaRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
    }
	
	/**
	 * Cenário de teste - quando o valor for zero
	 */
    @Test
    public void valorForZero() throws Exception {
    	
        var transferenciaRequestDTO = new TransferenciaRequestDTO("1234567890", "0987654321", BigDecimal.ZERO, LocalDate.now(Clock.systemDefaultZone()));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transferencias-app-bff/agendar")
        	.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferenciaRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
    }
	
}