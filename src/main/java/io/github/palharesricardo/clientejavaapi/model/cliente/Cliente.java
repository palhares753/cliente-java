package io.github.palharesricardo.clientejavaapi.model.cliente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import io.github.palharesricardo.clientejavaapi.dto.model.cliente.clienteDTO;
import io.github.palharesricardo.clientejavaapi.enumeration.clienteTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that implements the cliente structure.
 * 
 * @author Ricardo Palhares
 * @since 01/04/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class cliente implements Serializable {
	
	private static final long serialVersionUID = -3656431259068389491L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cpf;
	
	private String nome;
	
	private String endereco;
	
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	
	public cliente (clienteTypeEnum type){
		this.type = type;
	}
	
	/**
	 * Method to convert an cliente entity to a cliente DTO.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return a <code>clienteDTO</code> object
	 */
	public clienteDTO convertEntityToDTO() {
		return new ModelMapper().map(this, clienteDTO.class);
	}
	
}
