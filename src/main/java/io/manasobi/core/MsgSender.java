package io.manasobi.core;

import io.manasobi.Point;
import io.manasobi.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.internals.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by manasobi on 2017-04-15.
 */
@Slf4j
@Component
public class MsgSender<T> {

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public void send(T message) {

        // the KafkaTemplate provides asynchronous send methods returning a
        // Future
        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(KafkaConfig.TOPIC, generateKey(), message);

        // you can register a callback with the listener to receive the result
        // of the send asynchronously
        future.addCallback(new ListenableFutureCallback<SendResult<String, T>>() {

            @Override
            public void onSuccess(SendResult<String, T> result) {
                //log.info("sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", message, ex);
            }
        });

        // alternatively, to block the sending thread, to await the result,
        // invoke the future's get() method
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
