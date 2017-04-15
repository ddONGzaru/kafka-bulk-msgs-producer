package io.manasobi.core;

import io.manasobi.config.KafkaConfig;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by twjang on 15. 9. 30.
 */
@Slf4j
@Component
public class KafkaTaskManager {

    public void process(TextArea console) {

        console.clear();

        log.debug("===================================");
        log.debug("Application :: Start..." );
        log.debug("Kafka Producer :: Topic -> " + KafkaConfig.TOPIC);


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        int producerThreadNums = 10;

        for (int i = 1; i <= producerThreadNums; i++) {

            Runnable worker = new KafkaTaskWorker(i);

            executor.execute(worker);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {}

        stopWatch.stop();

        //console.write(logTimestamp + "Application :: End...\n");
        //console.write(logTimestamp + "Kafka Producer :: elapsed time  -> " + stopWatch.getTotalTimeMillis() + "\n");

        log.debug("Application :: End...");
        log.debug("Kafka Producer :: elapsed time  -> "
                + Double.parseDouble(stopWatch.getTotalTimeMillis() / 1000 + "." + stopWatch.getTotalTimeMillis() % 1000));
        log.debug("===================================");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Anypoint Kafka Producer ver-1.0.3");
        alert.setHeaderText("작업이 완료되었습니다.");
        alert.setContentText("Kafka Producer :: elapsed time  -> "
                + Double.parseDouble(stopWatch.getTotalTimeMillis() / 1000 + "." + stopWatch.getTotalTimeMillis() % 1000));

        alert.showAndWait();

    }



}