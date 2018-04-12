package org.jivesoftware.openfire.mq;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.dom4j.Element;
import org.dom4j.io.XMPPPacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

/**
 * 
 *
 * @author fupeng-ds
 */
public class KafkaConsumer implements Consumer {
	
	private static Logger log = LoggerFactory.getLogger(KafkaConsumer.class); 
	
	private static final String SERVER = "192.168.56.101:9092";
	
	private static final String TOPIC = "test";

	private org.apache.kafka.clients.consumer.Consumer consumer;
	
	private XMPPPacketReader reader;
	
	public KafkaConsumer(XMPPPacketReader reader) {
		Properties props = new Properties();  
        props.put("bootstrap.servers", SERVER);
        props.put("group.id", "test");  
        props.put("enable.auto.commit", "true");  
        props.put("auto.commit.interval.ms", "1000");  
        props.put("session.timeout.ms", "30000");  
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        this.consumer.subscribe(Arrays.asList(TOPIC));
        this.reader = reader;
	}

	@Override
	public Message receive() {
		ConsumerRecords<String, String> records = consumer.poll(1);
		Message packet = null;
		for (ConsumerRecord<String, String> record : records) {
			log.info("recive message : " + record);
			String value = record.value();
			try {
				Element doc = reader.read(new StringReader(value)).getRootElement();
				packet = new Message(doc, true);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return packet;
	}

}
