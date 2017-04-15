package io.manasobi.support;

import com.google.common.collect.Lists;
import io.manasobi.domain.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Component
public class PayloadWorker {

    private PayloadBuilder<Point> payloadBuilder;

    @Autowired
    public void setPayloadBuilder(PayloadBuilder<Point> payloadBuilder) {
        this.payloadBuilder = payloadBuilder;
    }

    public List<Point> payloadList = Lists.newArrayList();

    public List<Point> work(int jobCount) {

        for (int i = 0; i < jobCount; i++) {
            payloadList.add(payloadBuilder.build());
        }

        return payloadList;
    }

}
