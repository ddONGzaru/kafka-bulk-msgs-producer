package io.manasobi.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.manasobi.config.KafkaConfig;
import io.manasobi.domain.Point;
import io.manasobi.utils.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by twjang on 15. 10. 7.
 */
@Slf4j
public class KafkaTaskWorker implements Runnable {

    private KafkaTemplate<String, JsonNode> kafkaTemplate;

    private int index;

    KafkaTaskWorker(int index) {
        kafkaTemplate = AppContextManager.getBean("kafkaTemplate", KafkaTemplate.class);
        this.index = index;
    }

    @Override
    public void run() {

        DataSetReader reader = new DataSetReader();

        List<Point> messageList = reader.read(KafkaConfig.DATASET_DATE_TAG, KafkaConfig.MSG_TOTAL_SIZE / 10, index);

        ObjectMapper objectMapper = new ObjectMapper();

        messageList.forEach(msg -> {
            kafkaTemplate.send(KafkaConfig.TOPIC, generateKey(), objectMapper.convertValue(msg, JsonNode.class));
        });

        log.debug("Dataset 레코드 총계: {}", NumberFormat.getNumberInstance().format(messageList.size()));

    }

    private AtomicInteger keyCounter = new AtomicInteger(0);

    private String generateKey() {

        int keyIndex = keyCounter.getAndIncrement();

        if (keyIndex == Integer.MAX_VALUE) {
            keyCounter.set(0);
            return "0";
        }

        return Integer.toString(keyIndex);
    }

}
