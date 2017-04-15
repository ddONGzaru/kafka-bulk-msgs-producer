package io.manasobi.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by manasobi on 2017-04-14.
 */
@Configuration
public class KafkaConfig {

    public static String METADATA_BROKER_LIST ;

    public static String TOPIC ;

    public static String DATASET_DATE_TAG ;

    public static int MSG_TOTAL_SIZE;

    public static int MSG_MAX_ROWS = 5000;
}
