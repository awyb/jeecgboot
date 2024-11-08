package com.whu.servicedataclient.project.service;

import com.google.gson.Gson;
import com.whu.servicedataclient.project.entity.AcLineSegment;
import com.whu.servicedataclient.project.mapper.DataMapper;
import com.whu.servicedataclient.util.IDUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Service
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public void sendMsg(Map<String, Object> map) {
        String str = new Gson().toJson(map);
        System.out.print("map = " + str);

        Properties prop = new Properties();

//        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.110.253:9092");
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        prop.put(ProducerConfig.RETRIES_CONFIG, 0);
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        prop.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        String topic = "ocs_nn_aclinesegment";

        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        producer.send(new ProducerRecord<String, String>(topic, "1", str));
        producer.close();
//        props.put("bootstrap.servers", " 10.45.27.180:9092, 10.45.27.181:9092, 10.45.27.182:9092");
//        props.put("auto.offset.reset", "earliest");
//        props.put("enable.auto.commit", "false");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//     s
//        props.put("sasl.jaas.config",
//                "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"用户名 \" password=\"密码\";");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
//        consumer.subscribe(Arrays.asList("ocs_nn_breaker"));
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(10000);
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(record);
//            }
//        }

    }

    public void test1() {
        AcLineSegment acLineSegment = new AcLineSegment();
        acLineSegment.setId(IDUtil.generate());
        acLineSegment.setSubstationname("123");
        dataMapper.insertAcLine(acLineSegment);
    }
}
