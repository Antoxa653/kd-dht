package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.FindValueRequestMessage;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;

public class FindValueRequestMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		FindValueRequestMessage findValueMessage = (FindValueRequestMessage) message;
		buffer.put(findValueMessage.getSenderId());
		buffer.put(findValueMessage.getLookUpId());
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
		byte[] lookUpId = new byte[idSize];
		buffer.get(lookUpId);
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		FindValueRequestMessage message = new FindValueRequestMessage(type, command, senderId, lookUpId, randomId);
		return message;
	}

}
