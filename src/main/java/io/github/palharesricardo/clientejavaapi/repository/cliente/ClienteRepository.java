package io.github.palharesricardo.clientejavaapi.repository.cliente;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.palharesricardo.clientejavaapi.model.cliente.cliente;

/**
 * Interface that implements the cliente Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Repository
public interface clienteRepository extends JpaRepository<cliente, Long> {

	/**
	 * Method to search for all the cliente in the same cpf (unique number).
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>Optional<cliente></code> object
	 */
	Optional<cliente> findByCpf(String cpf);
	
}
