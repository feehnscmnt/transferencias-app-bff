package br.com.transferenciasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.transferenciasapp.entity.TransferenciaEntity;

/**
 * Interface repository para implementação dos métodos de validação do
 * cadastro e dos métodos de consulta das transferências.
 * 
 * @author Felipe Nascimento
 * 
 */

public interface TransferenciaRepository extends JpaRepository<TransferenciaEntity, Long> {}