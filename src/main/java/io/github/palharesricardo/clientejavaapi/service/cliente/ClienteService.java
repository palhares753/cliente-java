package io.github.palharesricardo.clientejavaapi.service.travel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.palharesricardo.clientejavaapi.dto.model.travel.TravelDTO;
import io.github.palharesricardo.clientejavaapi.exception.TravelNotFoundException;
import io.github.palharesricardo.clientejavaapi.model.travel.Travel;

/**
 * Service Interface that provides methods for manipulating Travel objects.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public interface TravelService {
	
	/**
	 * Method that save an object Travel.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param travel
	 * @return <code>Travel</code> object
	 */
	Travel save(Travel travel);
	
	/**
	 * Method that remove an object Travel by an id.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param travelId
	 */
	void deleteById(Long travelId);
	
	/**
	 * Method that find an object Travel by an id.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param id
	 * @return <code>Optional<Travel></code> object
	 */
	Travel findById(Long id) throws TravelNotFoundException;
	
	/**
	 * Method that find one or more trips by orderNumber.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param orderNumber
	 * @return <code>Optional<Travel></code> object
	 */
	Optional<Travel> findByOrderNumber(String orderNumber);
	
	/**
	 * Method that find all travels.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>List<Travels></code> object
	 */
	List<Travel> findAll();
	
	/**
	 * Method that find all travels in a period of time.
	 * 
	 * @author Ricardo Palhares
	 * @since 21/08/2020
	 * 
	 * @param startDate - the start date of the search
	 * @param endDate - the end date of the search
	 * @param pageable - object for pagination information: the page that will be return in the search, 
	 * the size of page, and sort direction that the results should be shown: ASC - ascending order; 
	 * DESC - descending order.
	 * 
	 * @return <code>Page<Travel></code> object
	 */
	Page<Travel> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
	/**
	 * Method to build a partial response in requests regarding Travel.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param fields
	 * @param dto
	 * @return a <code>TravelDTO</code> object
	 */
	TravelDTO getPartialJsonResponse(String fields, TravelDTO dto);
}
