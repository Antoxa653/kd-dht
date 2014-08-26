package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PingPongMessage;

public class PingPongMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		PingPongMessage pingPongMessage = (PingPongMessage) message;
		buffer.put(pingPongMessage.getSenderId());
		buffer.put(pingPongMessage.getEchoedRandomId());
		buffer.put(pingPongMessage.getRandomId());
		buffer.flip();
		return buffer;
	}

	@Override
	public PingPongMessage deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = KdConfiguration.get(KdConstants.KEY_SIZE_PROPERTY);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] echoedRandomId = new byte[idSize];
		buffer.get(echoedRandomId);
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		PingPongMessage message = new PingPongMessage(type, command, senderId, echoedRandomId, randomId);
		return message;
	}
}
