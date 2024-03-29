package ecommandes.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import leadware.command.domain.OrderDetail;

	@Configuration
	@EnableKafka
	public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;
		
	@Bean
	public ConsumerFactory<String, OrderDetail> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
			       
		 props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	       // props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomOrderDetailDeserializer.class);
	        //props.put(CustomOrderDetailDeserializer.TRUSTED_PACKAGES, "leadware.command.domain");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	        
	        JsonDeserializer<OrderDetail> jsonDeserializer = new JsonDeserializer<>(OrderDetail.class);
	        jsonDeserializer.addTrustedPackages("leadware.command.domain");
	    	return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
	    
	}
	
/*	@Bean
    public ConsumerFactory<String, OrderDetail> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }*/
	
	@Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderDetail>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderDetail> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
