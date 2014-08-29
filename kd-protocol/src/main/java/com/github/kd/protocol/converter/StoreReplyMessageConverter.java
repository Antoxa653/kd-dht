package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PongMessage;
import com.github.kd.protocol.StoreReplyMessage;
import com.github.kd.protocol.StoreStatusMessage;

public class StoreReplyMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		StoreReplyMessage findValueMessage = (StoreReplyMessage) message;
		buffer.put(findValueMessage.getSenderId());
		buffer.put(findValueMessage.getEchoedRandomId());
		buffer.put(findValueMessage.getStatusMessage());
		buffer.put(findValueMessage.getRandomId());
		buffer.flip();
		return buffer;
	}

	@Override
	public Message deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = KdConfiguration.get(KdConstants.KEY_SIZE_PROPERTY);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] echoedRandomId = new byte[idSize];
		buffer.get(echoedRandomId);
		byte statusMessage = buffer.get();		
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		StoreReplyMessage message = new StoreReplyMessage(type, command, senderId, echoedRandomId,
				StoreStatusMessage.valueOf(statusMessage),
				randomId);
		return message;
	}

}
