package io.github.palharesricardo.clientejavaapi.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import io.github.palharesricardo.clientejavaapi.dto.model.user.UserDTO;
import io.github.palharesricardo.clientejavaapi.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements an User entity in the API.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 5514528747731992863L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	public User(Long id) {
		this.id = id;	
	}
	
	/**
	 * Method that verifies if the user is admin
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return boolean
	 */
	public boolean isAdmin() {
		return RoleEnum.ROLE_ADMIN.toString().equals(this.role.toString());
	}
	
	/**
	 * Method to convert an User entity to an User DTO
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param user
	 * @return an UserDTO object
	 */
	public UserDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserDTO.class);
	}

}
