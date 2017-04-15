package io.manasobi.config;

import com.fasterxml.jackson.databind.JsonNode;
import io.manasobi.core.RoundRobinPartitioner;
import io.manasobi.domain.AvroSerializer;
import io.manasobi.domain.Point;
import io.manasobi.domain.PointSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tw.jang on 2017-04-14.
 */
@Configuration
@EnableKafka
@Import(AppConfig.class)
public class KafkaProducerConfig {

    @Autowired
    private MessageSource messageSource;

    @Bean
    public ProducerFactory<String, Point> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {

        KafkaConfig.METADATA_BROKER_LIST = messageSource.getMessage("text.kafka.broker.url", null, Locale.getDefault());

        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.METADATA_BROKER_LIST);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        return props;
    }

    @Bean
    public KafkaTemplate<String, Point> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }
}