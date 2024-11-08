package com.whu.servicedataclient;

import com.google.gson.Gson;
import com.whu.servicedataclient.project.entity.AcLineSegment;
import com.whu.servicedataclient.project.entity.SynchronousMachine;
import com.whu.servicedataclient.project.service.DataDealService;
import com.whu.servicedataclient.util.Global;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication

public class ServiceDataClientApplication {

    public static void main(String[] args) {
        Global.ac = SpringApplication.run(ServiceDataClientApplication.class, args);
        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.110.253:9092");
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("auto.offset.reset", "latest");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "nanning");
//        props.put("security.protocol", "SASL_PLAINTEXT");//明文模式
        props.put("sasl.mechanism","PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"visitor\" password=\"qaz@123\";");
        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        List<String> topics = new ArrayList<String>();
        topics.add("ocs_nn_aclinesegment");
        topics.add("ocs_nn_synchronousmachine");
        consumer.subscribe(topics);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> record : records) {
                String res = record.value();
                System.out.println(res);
                if ("ocs_nn_aclinesegment".equals(record.topic())) {
                    //
                    DataDealService.insertAcLine(res);
                } else if ("ocs_nn_synchronousmachine".equals(record.topic())) {

                }
            }
        }
    }

}