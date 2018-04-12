package org.jivesoftware.openfire.mq;

import org.xmpp.packet.Message;

/**
 * 
 *
 * @author fupeng-ds
 */
public interface Producer {
	
	void send(Message packet);

}
