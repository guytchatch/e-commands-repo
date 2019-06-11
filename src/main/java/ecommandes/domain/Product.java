package ecommandes.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Cette classe représente un produit
 * @author landry
 *
 */
@Data
@RequiredArgsConstructor
@Entity
@ApiModel(description = "Tous les détails d'un produit ")
public final class Product{

	//Identifiant du produit
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Code du produit généré par la BD", required=false, hidden=true)
	private Integer code;
	
	//Libelé du produit
    @ApiModelProperty(notes = "libelé du produit")
	private final String label;
	
	//Prix du produit
    @ApiModelProperty(notes = "Prix du produit")
	private final Integer price;
	
	//Quantité maximale du produit
    @ApiModelProperty(notes = "Quantité maximal du produit en stock")
	private final Integer maxQuantity;
	
	//Liste des stock d'un produit
	@OneToMany(/* mappedBy="product", */fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "Liste des stocks",hidden=true )
	private Set<ProductStock> productStockList = new LinkedHashSet<ProductStock>();
	
    // Constructeur sans argument pour JSON/JPA
	protected Product() {
		label = null;
		price = null;
		maxQuantity = null;
		productStockList = null;
	}
	
	/**
	 * Fonction toString
	 */
	/*public String toString() {
		return "{code:" + code + 
				",label:"+label+
				",price:" + price + 
				",maxQuantity:" + maxQuantity
				+"}";
	 }*/
}
