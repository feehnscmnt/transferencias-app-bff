package br.com.transferenciasapp.service;

import br.com.transferenciasapp.repository.TransferenciaRepository;
import org.springframework.transaction.annotation.Transactional;
import br.com.transferenciasapp.dto.TransferenciaResponseDTO;
import br.com.transferenciasapp.dto.TransferenciaRequestDTO;
import br.com.transferenciasapp.entity.TransferenciaEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.Clock;
import java.util.List;

/**
 * Classe service responsável pelo agendamento das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

@Service
@RequiredArgsConstructor
public class TransferenciaService {
	private final TransferenciaRepository repository;
	private final CalculadoraTaxasService calculadora;
	private Clock clock = Clock.systemDefaultZone();
	
	/**
	 * Método responsável pela persistência dos dados efetuando o agendamento das transferências.
	 * 
	 * @param transferenciaRequestDTO - objeto com os dados da transferência
	 * 
	 * @return objeto com os dados do agendamento
	 * 
	 */
	@Transactional
    public TransferenciaResponseDTO agendar(TransferenciaRequestDTO transferenciaRequestDTO) {
		
        var hoje = LocalDate.now(clock);
        var dias = ChronoUnit.DAYS.between(hoje, transferenciaRequestDTO.getDataTransferencia());
        var taxa = calculadora.calcular(transferenciaRequestDTO.getValor(), dias);
        
        var transferenciaEntity = new TransferenciaEntity(
        	transferenciaRequestDTO.getContaOrigem(), 
        	transferenciaRequestDTO.getContaDestino(), 
        	transferenciaRequestDTO.getValor(), 
            taxa, 
            transferenciaRequestDTO.getDataTransferencia(), 
            hoje
        );
        
        var transferenciaSalva = repository.save(transferenciaEntity);
        
        return new TransferenciaResponseDTO(transferenciaSalva);
        
    }
	
	/**
	 * Método responsável pela persistência dos dados listando os agendamentos efetuados.
	 * 
	 * @return lista de agendamentos efetuados
	 * 
	 */
	@Transactional(readOnly = true)
    public List<TransferenciaResponseDTO> listar() {
		
		var sort = Sort.by(Sort.Direction.DESC, "dataAgendamento").and(Sort.by(Sort.Direction.DESC, "id"));
        
        return repository.findAll(sort).stream().map(TransferenciaResponseDTO::new).collect(Collectors.toList());
        
    }
	
}