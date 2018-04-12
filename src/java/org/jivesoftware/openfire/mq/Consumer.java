package org.jivesoftware.openfire.mq;

import org.xmpp.packet.Message;

/**
 * 
 *
 * @author fupeng-ds
 */
public interface Consumer {

	Message receive();
	
}
