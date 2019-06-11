package ecommandes.event;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ecommandes.service.ProductService;
import leadware.command.domain.OrderDetail;

/**
 * Classe permettant de recupérer les message envoyés
 * @author landry
 *
 */
@Component
public class OrderDetailListener {

	//déclaration du productService
	private ProductService productService;
	
	//Logger
    private final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	//Injection du bean par le constructeur
	@Autowired
	public OrderDetailListener(final ProductService productService){
		
		//Initialisation
		this.productService = productService;
		
	}
	
	@KafkaListener(topics="commandTopic", groupId = "group_id", containerFactory="kafkaListenerContainerFactory")
	public void handleDetailOrderEvent(OrderDetail orderDetail) {
		
		//Log
        logger.info(String.format("#### -> Consumed message -> %s", orderDetail));
		
		productService.productDelivery(orderDetail.getProductCode(), orderDetail.getQuantity());
	}
}
