package br.com.transferenciasapp.exception;

/**
 * Classe exception responsável pela mensagens de exceções personalizadas.
 * 
 * @author Felipe Nascimento
 * 
 */

public class RegraNegocioException extends RuntimeException {
	private static final long serialVersionUID = 2326712051847805096L;
	
	public RegraNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
	
}