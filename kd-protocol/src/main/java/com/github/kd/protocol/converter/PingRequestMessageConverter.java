package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PingRequestMessage;

public class PingRequestMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		PingRequestMessage pingMessage = (PingRequestMessage) message;
		buffer.put(pingMessage.getSenderId());
		buffer.put(pingMessage.getRandomId());
		buffer.flip();
		return buffer;
	}

	@Override
	public PingRequestMessage deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = getKdProperties().getValue(KdConstants.KEY_SIZE_PROPERTY);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		PingRequestMessage message = new PingRequestMessage(type, command, senderId, randomId);
		return message;
	}
}
