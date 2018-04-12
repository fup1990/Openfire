package org.jivesoftware.openfire.net;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.openfire.Connection;
import org.jivesoftware.openfire.PacketRouter;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.mq.KafkaProducer;
import org.jivesoftware.openfire.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

/**
 * 
 * @author fupeng-ds
 */
public class MQClientStanzaHandler extends ClientStanzaHandler {
	
	private static final Logger Log = LoggerFactory.getLogger(MQClientStanzaHandler.class);
	
	private Producer producer;

	public MQClientStanzaHandler(PacketRouter router, Connection connection) {
		super(router, connection);
		// create mq producer
		producer = new KafkaProducer();
	}

	@Override
	protected void processMessage(Message packet) throws UnauthorizedException {
		packet.setFrom(session.getAddress());
		String body = packet.getBody();
		if (StringUtils.isNotBlank(body)) {
			Log.info("Message is : " + body);
			// 推入MQ
			producer.send(packet);
		}
		session.incrementClientPacketCount();
	}
	
}
