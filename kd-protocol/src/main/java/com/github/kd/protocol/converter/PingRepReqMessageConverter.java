package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PingRepReqMessage;

public class PingRepReqMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer type = super.serialize(message);
		buffer.put(type);
		PingRepReqMessage pingMessage = (PingRepReqMessage) message;
		buffer.put(pingMessage.getSenderId());
		buffer.put(pingMessage.getEchoedRandomId());
		buffer.put(pingMessage.getRandomId());
		buffer.flip();
		return buffer;
	}

	@Override
	public PingRepReqMessage deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = getKdProperties().getValue(KdConstants.KEY_SIZE_PROPERTY);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] echoedRandomId = new byte[idSize];
		buffer.get(echoedRandomId);
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		PingRepReqMessage message = new PingRepReqMessage(type, command, senderId, echoedRandomId, randomId);
		return message;
	}
}