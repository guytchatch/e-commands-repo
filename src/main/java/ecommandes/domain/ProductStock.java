package ecommandes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.joda.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * Classe représentant un stock de produits
 * @author landry
 *
 */
@Data
@RequiredArgsConstructor
@Entity
@ApiModel(description = "Tous les détails du stock (ProductStock) ")
public final class ProductStock {
	
	//Code du stock
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Code du stock généré par la BD", hidden=true)
	private Integer code;
	
	//Produit lié au stock
	/*@ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="product_code")
	private final Product product;*/
	
	//Date de livraison du stock
	// Will be mapped as DATETIME (on MySQL)
	//@JsonDeserialize(using=CustomDateDeserializer.class)
	//@JsonSerialize(using=CustomDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @ApiModelProperty(notes = "Date de livraison, générée par la BD", hidden=true, required=false)
	private LocalDate deliveryDate;
	
	//Date de péremption des produits
	// Will be mapped as DATE (on MySQL), i.e. only date without timestamp
	//@JsonDeserialize(using=CustomDateDeserializer.class)
	//@JsonSerialize(using=CustomDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @ApiModelProperty(notes = "Date de péremption du produit", example="2020-05-18")
	private final LocalDate expirationDate;
	
	//Quantité du produit en stock
    @ApiModelProperty(notes = "Quantité du stock")
	private Integer quantity;
	
	//Qualité des produits
    @ApiModelProperty(notes = "Enumère les Qualités possible du produit (TRES_BONNE_QUALITE, BONNE_QUALITE, QUALITE_MOYENNE)")
	private final Quality quality;  
	
	public static enum Quality{
		TRES_BONNE_QUALITE, BONNE_QUALITE, QUALITE_MOYENNE
	}
	
    // Constructeur sans argument pour JSON/JPA
	protected ProductStock() {
		//product = null;
		expirationDate = null;
		quality = null;
	}
	
	/*
	 * Méthode permettant de persister la date 
	 * de livraison au moment de son enregistrement
	 */
	 @PrePersist
	  void deliveryDate() {
	    this.deliveryDate = new LocalDate();
	  }
	 
	 /**
	  * Fonction toString
	  */
	/* @Override
	public String toString() {
		return "{code:" + code + 
				",deliveryDate:" + deliveryDate + 
				",expirationDate:" + expirationDate+
				",quantity:" + quantity+
				",quality:"+quality
				+"}";
	 }*/
	 
	 /**
	  * Renvoie un code de hachage en fonction de la date d'expiration d'un produit
	  */
	 @Override
	 public int hashCode() {
		 return expirationDate.hashCode();
	 }
}
