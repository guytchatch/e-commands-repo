package ecommandes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Cette classe représente un client
 * @author landry
 *
 */
@Data
@RequiredArgsConstructor
@Entity
@ApiModel(description = "Tous les détails d'un client ")
public class Client {

	//Identifiant du client
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Code du client généré par la BD")
	private Integer code;
	
	//Nom du client
	@NotNull
    @Size(min = 3, max = 40)
    @ApiModelProperty(notes = "Nom complet du client")
	private final String name;
	
	//Numéro de téléphone du client
    @ApiModelProperty(notes = "Numéro de téléphone du client")
	private final Integer phone;
	
	//email du client
    @ApiModelProperty(notes = "Email du client")
	private final String  email;
	
    // Constructeur sans argument pour JSON/JPA
	protected Client() {
		name = null;
		phone = null;
		email = null;
	}
}
