package io.github.palharesricardo.clientejavaapi.dto.model.cliente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.zero_x_baadf00d.partialize.annotation.Partialize;

import io.github.palharesricardo.clientejavaapi.model.cliente.cliente;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements cliente data transfer object (DTO)
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Partialize(allowedFields = {"id", "cpf", "nome", "endereco"})
public class clienteDTO extends RepresentationModel<clienteDTO> {
	
	private Long id;
	
	@NotNull(message="cpf Number cannot be null")
	@Length(min>6, message="cpf Number must contain at least 6 characters")
	private String cpf;

	@NotNull(message="Name cannot be null")
	@JsonSerialize(using = ToStringSerializer.class)
	private String nome;
	
	@NotNull(message="adress cannot be null")
	@JsonSerialize(using = ToStringSerializer.class)
	private String endereco;

	
	/**
	 * Method to convert an cliente DTO to a cliente entity.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param dto
	 * @return a <code>cliente</code> object
	 */
	public cliente convertDTOToEntity() {
		return new ModelMapper().map(this, cliente.class);
	}

}
