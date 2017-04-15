package io.manasobi.support;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.KryoObjectOutput;
import com.google.common.collect.Lists;
import io.manasobi.domain.Point;
import io.manasobi.utils.DateUtils;
import io.manasobi.utils.FileUtils;
import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Slf4j
public class DataSetWriter {

    public void write(int size) {

        PointPayloadBuilder payloadBuilder = new PointPayloadBuilder();

        PayloadWorker worker = new PayloadWorker();
        worker.setPayloadBuilder(payloadBuilder);

        List<Point> pointList = worker.work(size);

        int page = 0;
        int unitSize = size / 10;

        List<List<Point>> resultList = Lists.newArrayList();

        while(size != page * unitSize) {

            List<Point> listUnit = pointList.stream()
                                            .skip(page * unitSize)
                                            .limit(unitSize)
                                            .collect(Collectors.toCollection(ArrayList::new));

            resultList.add(listUnit);

            page++;
        }

        Kryo kryo = new Kryo();

        try {

            for (int i = 1; i <= 10; i++) {
                @Cleanup FileOutputStream fos = new FileOutputStream(buildDataSetName(unitSize, i));
                @Cleanup ByteBufferOutput output = new ByteBufferOutput(fos);

                KryoObjectOutput objectOutput = new KryoObjectOutput(kryo, output);
                objectOutput.writeObject(resultList.get(i-1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String buildDataSetName(int size, int index) {

        String userDir = System.getProperty("user.dir");

        String dir = userDir + "/src/main/resources/dataset/";

        String namePrefix = DateUtils.getNow("yyyyMMdd");

        String name = namePrefix + "_point-msg_size_" + String.format("%06d", size) + "_index_" + String.format("%02d", index) + ".jdo";

        return dir + name;
    }

    enum Size {

        _100_000(100000), _300_000(300000), _500_000(500000), _1_000_000(1000000), _3_000_000(3000000), _5_000_000(5000000);

        @Getter
        int size;

        Size(int size) {
            this.size = size;
        }
    }

    public static void main(String[] args) {

        DataSetWriter dataSetWriter = new DataSetWriter();
        dataSetWriter.write(Size._1_000_000.getSize());
    }

}
