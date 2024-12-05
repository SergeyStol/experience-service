package by.javaguru.experienceservice.infrastructure.kafka;

import by.javaguru.experienceservice.features.experience.ExperienceAggregatorModel;
import by.javaguru.experienceservice.features.industry.IndustryDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-12-05
 */
@Configuration
public class KafkaConfig {
   @Value("${spring.kafka.consumer.group-id}")
   private String groupId;

   // UUID : LONG PRODUCER ********************************************************
   @Bean
   public ProducerFactory<UUID, Long> uuidLongProducerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaProducerFactory<>(configProps);
   }

   @Bean
   public KafkaTemplate<UUID, Long> uuidLongKafkaTemplate() {
      return new KafkaTemplate<>(uuidLongProducerFactory());
   }

   // UUID : String PRODUCER ********************************************************
   @Bean
   public ProducerFactory<UUID, String> uuidStringProducerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaProducerFactory<>(configProps);
   }

   @Bean
   public KafkaTemplate<UUID, String> uuidStringKafkaTemplate() {
      return new KafkaTemplate<>(uuidStringProducerFactory());
   }

   // UUID : String CONSUMER ********************************************************
   @Bean
   public ConcurrentKafkaListenerContainerFactory<UUID, String> uuidStringKafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<UUID, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(uuidStringConsumerFactory());
      return factory;
   }

   @Bean
   public ConsumerFactory<UUID, String> uuidStringConsumerFactory() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaConsumerFactory<>(props);
   }

   // UUID : ExperienceAggregatorModel PRODUCER ********************************************************
   @Bean
   public ProducerFactory<UUID, ExperienceAggregatorModel>
   uuidExperienceAggregatorModelProducerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ExperienceAggregatorModelSerializer.class);
      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaProducerFactory<>(configProps);
   }

   @Bean
   public KafkaTemplate<UUID, ExperienceAggregatorModel>
   uuidExperienceAggregatorModelKafkaTemplate() {
      return new KafkaTemplate<>(uuidExperienceAggregatorModelProducerFactory());
   }

   // UUID : ExperienceAggregatorModel CONSUMER ********************************************************
   @Bean
   public ConcurrentKafkaListenerContainerFactory<UUID, ExperienceAggregatorModel>
   uuidExperienceAggregatorModelKafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<UUID, ExperienceAggregatorModel> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(uuidExperienceAggregatorModelConsumerFactory());
      return factory;
   }

   @Bean
   public ConsumerFactory<UUID, ExperienceAggregatorModel> uuidExperienceAggregatorModelConsumerFactory() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        ExperienceAggregatorModelDeserializer.class);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaConsumerFactory<>(props);
   }

   // UUID : IndustryDto CONSUMER ********************************************************
   @Bean
   public ConcurrentKafkaListenerContainerFactory<UUID, IndustryDto>
   uuidIndustryDtoKafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<UUID, IndustryDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(uuidIndustryDtoModelConsumerFactory());
      return factory;
   }

   @Bean
   public ConsumerFactory<UUID, IndustryDto> uuidIndustryDtoModelConsumerFactory() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        IndustryDtoDeserializer.class);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      return new DefaultKafkaConsumerFactory<>(props);
   }
}
