package io.manasobi.core;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015-09-28.
 */
public class RoundRobinPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);

        int partitionsCount = partitions.size();

        int id = Integer.parseInt(key.toString());

        if (id < 0) {
            return new Random().nextInt(partitionsCount);
        }

        int partitionId = id % partitionsCount;

        return partitionId;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
