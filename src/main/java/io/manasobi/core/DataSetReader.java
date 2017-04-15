package io.manasobi.core;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.KryoObjectInput;
import io.manasobi.domain.Point;
import io.manasobi.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by twjang on 15. 10. 22.
 */
@Slf4j
public class DataSetReader {

    public List<Point> read(String datasetDate, int size, int index) {

        String fileDir;
        String userDir = System.getProperty("user.dir");

        if (FileUtils.existsDir(userDir + "/src/main/resources/dataset/")) {
            fileDir = userDir + "/src/main/resources/dataset/";
        } else {
            fileDir = userDir + "/config/dataset/";
        }

        String fileName = datasetDate + "_point-msg_size_" + String.format("%06d", size) + "_index_" + String.format("%02d", index) + ".jdo";

        List<Point> objList = null;

        Kryo kryo = new Kryo();

        try(FileInputStream fis = new FileInputStream(fileDir + fileName);
            ByteBufferInput input = new ByteBufferInput(fis)) {

            KryoObjectInput objectInput = new KryoObjectInput(kryo, input);

            objList = (List<Point>) objectInput.readObject();

        } catch(IOException | ClassNotFoundException e) {

            log.error(e.getMessage());
            return null;
        }

        return objList;
    }
}