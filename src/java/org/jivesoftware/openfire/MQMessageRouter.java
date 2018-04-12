package org.jivesoftware.openfire;

import org.jivesoftware.openfire.container.BasicModule;
import org.jivesoftware.openfire.mq.Consumer;
import org.jivesoftware.openfire.mq.KafkaConsumer;
import org.jivesoftware.openfire.nio.ConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

/**
 * 
 *
 * @author fupeng-ds
 */
public class MQMessageRouter extends BasicModule {
	
	private MessageRouter messageRouter;
	
	private static Logger log = LoggerFactory.getLogger(MQMessageRouter.class); 
	
	public MQMessageRouter() {
		super("XMPP MQ Message Router");
	}

	@Override
	public void initialize(XMPPServer server) {
		super.initialize(server);
		messageRouter = server.getMessageRouter();
		// create mq consumer
		final Consumer consumer = new KafkaConsumer(ConnectionHandler.getXMPPPacketReader());
        Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					Message packet = consumer.receive();
					if (packet != null) {
						messageRouter.route(packet);
					}
				}
			}
        	
        };
        thread.setDaemon(true);
		thread.start();
		log.info("mq consumer thread started!");
	}

	
	
}
