package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PingReplyMessage;

public class PingReplyMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer type = super.serialize(message);
		buffer.put(type);
		PingReplyMessage pingMessage = (PingReplyMessage) message;
		buffer.put(pingMessage.getSenderId());
		buffer.put(pingMessage.getEchoedRandomId());
		buffer.flip();
		return buffer;
	}

	@Override
	public PingReplyMessage deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = getKdProperties().getValue(KdConstants.KEY_SIZE_PROPERTY);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] echoedRandomId = new byte[idSize];
		buffer.get(echoedRandomId);
		buffer.clear();
		PingReplyMessage message = new PingReplyMessage(type, command, senderId, echoedRandomId);
		return message;
	}

}
