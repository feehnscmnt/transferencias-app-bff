package br.com.transferenciasapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.transferenciasapp.dto.TransferenciaResponseDTO;
import br.com.transferenciasapp.service.TransferenciaService;
import br.com.transferenciasapp.dto.TransferenciaRequestDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import java.util.List;

/**
 * Classe controller responsável pela requisição do agendamento das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transferencias")
@CrossOrigin(origins = "http://localhost:4200")
public class TransferenciaController {
	private final TransferenciaService service;
	
	/**
	 * Método responsável pelo agendamento das transferências.
	 * 
	 * @param transferenciaRequestDTO - objeto com os dados da transferência
	 * 
	 * @return status 201 Created
	 * 
	 */
	@PostMapping("/agendar")
    public ResponseEntity<TransferenciaResponseDTO> agendar(@Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
		
        var response = service.agendar(transferenciaRequestDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }
	
	/**
	 * Método responsável pela listagem dos agendamentos realizados.
	 * 
	 * @return lista de agendamentos criada
	 * 
	 */
	@GetMapping("/listar")
    public ResponseEntity<List<TransferenciaResponseDTO>> listar() {
		
        var transferencias = service.listar();
        
        return ResponseEntity.ok(transferencias);
        
    }
	
}