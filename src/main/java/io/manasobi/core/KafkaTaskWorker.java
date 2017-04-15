package io.manasobi.core;

import io.manasobi.config.KafkaConfig;
import io.manasobi.domain.Point;
import io.manasobi.utils.AppContextManager;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by twjang on 15. 10. 7.
 */
@Slf4j
public class KafkaTaskWorker implements Runnable {

    private MsgSender<Point> msgSender;

    private int index;

    KafkaTaskWorker(int index) {
        msgSender = AppContextManager.getBean("msgSender", MsgSender.class);
        this.index = index;
    }

    @Override
    public void run() {

        DataSetReader reader = new DataSetReader();

        List<Point> messageList = reader.read(KafkaConfig.DATASET_DATE_TAG, KafkaConfig.MSG_TOTAL_SIZE / 10, index);

        messageList.forEach(msg -> msgSender.send(msg));

        log.debug("Dataset 레코드 총계: {}", NumberFormat.getNumberInstance().format(messageList.size()));
    }

}
