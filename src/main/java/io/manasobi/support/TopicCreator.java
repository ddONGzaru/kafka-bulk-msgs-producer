package io.manasobi.support;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;

import java.util.Properties;

/**
 * Created by twjang on 15. 9. 30.
 */
public class TopicCreator {

    private static final String ZK_SERVER = "192.168.0.100:2181";
    private static final String TOPIC_NAME = "bulk.msg.test.0002";

    private static final int NUM_PARTITIONS = 3;
    private static final int REPLICATION_FACTOR = 3;

    public static void main(String[] args) {

        int sessionTimeoutMs = 10000;
        int connectionTimeoutMs = 10000;

        ZkClient zkClient = new ZkClient(ZK_SERVER, sessionTimeoutMs, connectionTimeoutMs, ZKStringSerializer$.MODULE$);
        ZkUtils zkUtils = ZkUtils.apply(zkClient, false);

        AdminUtils.createTopic(zkUtils, TOPIC_NAME, NUM_PARTITIONS, REPLICATION_FACTOR, new Properties(), RackAwareMode.Disabled$.MODULE$);
    }

}
