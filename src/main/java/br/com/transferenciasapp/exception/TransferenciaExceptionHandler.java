package br.com.transferenciasapp.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.time.ZoneId;
import java.util.Map;

/**
 * Classe handler responsável pela configuração das mensagens de exceções personalizadas.
 * 
 * @author Felipe Nascimento
 * 
 */

@RestControllerAdvice
public class TransferenciaExceptionHandler {
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public Map<String, Object> regra(RegraNegocioException e) {
		
		return erro(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> validacao(MethodArgumentNotValidException e) {
		
		var message = e.getBindingResult()
			.getFieldErrors()
            .stream()
            .map(x -> x.getField() + ": " + x.getDefaultMessage())
            .collect(Collectors.joining("; "));
        
        return erro(HttpStatus.BAD_REQUEST.value(), message);
		
	}
	
	private Map<String, Object> erro(int status, String mensagem) {
		
		var m = new LinkedHashMap<String, Object>();
		
        m.put("timestamp", LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        m.put("status", status);
        m.put("mensagem", mensagem);
        
        return m;
		
	}
	
}