package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.protocol.Message;

public abstract class MessageConverter implements Converter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.put(message.getType().id());
		buffer.put(message.getCommand().id());
		buffer.flip();
		return buffer;
	}
}
