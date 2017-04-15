package io.manasobi.domain;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by tw.jang on 2017-04-09.
 */
public class PointSerializer implements Serializer<Point> {

    private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.addDefaultSerializer(Point.class, new KryoInternalSerializer());
            return kryo;
        };
    };

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Point data) {

        ByteBufferOutput output = new ByteBufferOutput(100);
        kryos.get().writeObject(output, data);
        return output.toBytes();
    }

    @Override
    public void close() {

    }

    private static class KryoInternalSerializer extends com.esotericsoftware.kryo.Serializer<Point> {
        @Override
        public void write(Kryo kryo, Output output, Point point) {

            output.writeLong(point.getTimestamp());

            output.writeString(point.getTagId());
            output.writeString(point.getTagName());
            output.writeString(point.getType());

            output.writeString(point.getValue());

            output.writeString(point.getSiteId());
            output.writeString(point.getOpcId());
            output.writeString(point.getGroupName());

            output.writeLong(point.getQuality());
            output.writeInt(point.getErrorCode());

        }

        @Override
        public Point read(Kryo kryo, Input input, Class<Point> clazz) {
            String id = input.readString();
            //Sensor.Type type = Sensor.Type.valueOf(input.readString());

            return new Point();
        }
    }
}
