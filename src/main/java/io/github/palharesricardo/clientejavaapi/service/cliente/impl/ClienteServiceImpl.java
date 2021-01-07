package io.github.palharesricardo.clientejavaapi.service.cliente.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.zero_x_baadf00d.partialize.Partialize;

import io.github.palharesricardo.clientejavaapi.dto.model.cliente.clienteDTO;
import io.github.palharesricardo.clientejavaapi.exception.clienteNotFoundException;
import io.github.palharesricardo.clientejavaapi.model.cliente.cliente;
import io.github.palharesricardo.clientejavaapi.repository.cliente.clienteRepository;
import io.github.palharesricardo.clientejavaapi.service.cliente.clienteService;

/**
 * Class that implements the cliente's service methods
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Service
public class clienteServiceImpl implements clienteService {
	
	clienteRepository clienteRepository;
	
	@Autowired
	public clienteServiceImpl(clienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	/**
	 * @see clienteService#save(cliente)
	 */
	@Override
	public cliente save(cliente cliente) {
		return clienteRepository.save(cliente);
	}

	/**
	 * @see clienteService#findByOrderNumber(String)
	 */
	@Override
	@Cacheable(value="clienteOrderNumberCache", key="#orderNumber", unless="#result==null")
	public Optional<cliente> findByOrderNumber(String orderNumber) {
		return clienteRepository.findByOrderNumber(orderNumber);
	}

	/**
	 * @see clienteService#deleteById(Long)
	 */
	@Override
	public void deleteById(Long clienteId) {
		clienteRepository.deleteById(clienteId);		
	}

	/**
	 * @see clienteService#findById(Long)
	 * @throws clienteNotFoundException 
	 */
	@Override
	@Cacheable(value="clienteIdCache", key="#id")
	public cliente findById(Long id) throws clienteNotFoundException {
		return clienteRepository.findById(id).orElseThrow(() -> 
			new clienteNotFoundException("cliente id=" + id + " not found"));
	}

	/**
	 * @see clienteService#findBetweenDates(LocalDateTime, LocalDateTime, Pageable)
	 */
	@Override
	public Page<cliente> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		return clienteRepository.
				findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate, 
					endDate, pageable);
	}

	/**
	 * @see clienteService#findAll()
	 */
	@Override
	public List<cliente> findAll() {
		return clienteRepository.findAll();
	}

	/**
	 * @see clienteService#getPartialJsonResponse(String, clienteDTO)
	 */
	@Override
	public clienteDTO getPartialJsonResponse(String fields, clienteDTO dto) {
		
		final Partialize partialize = new Partialize();
		final ContainerNode<?> node = partialize.buildPartialObject(fields, clienteDTO.class, dto);
		return new ObjectMapper().convertValue(node, clienteDTO.class);
	}

}
