package io.github.palharesricardo.clientejavaapi.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import io.github.palharesricardo.clientejavaapi.dto.model.user.UserAccountDTO;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements an UserAccount entity - to represents
 * an User and Account relationship in the Travels Java API.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_accounts")
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 7536502811640528298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
	
	/**
	 * Method to convert an User Transaction entity to an User Transaction DTO.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param userTransaction
	 * @return an UserTransactionDTO object
	 */
	public UserAccountDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserAccountDTO.class);
	}

}

 