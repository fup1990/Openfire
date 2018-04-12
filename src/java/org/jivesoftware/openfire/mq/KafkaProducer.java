package org.jivesoftware.openfire.mq;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.xmpp.packet.Message;

/**
 * 
 *
 * @author fupeng-ds
 */
public class KafkaProducer implements Producer {
	
	private org.apache.kafka.clients.producer.Producer<String, String> producer;
	
	private static final String SERVER = "192.168.56.101:9092";
	
	private static final String TOPIC = "test";
	
	public KafkaProducer() {
		Properties properties = new Properties();
        properties.put("bootstrap.servers", SERVER);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
	}

	@Override
	public void send(Message packet) {
		producer.send(new ProducerRecord<String, String>(TOPIC, packet.toString()));
	}

}
