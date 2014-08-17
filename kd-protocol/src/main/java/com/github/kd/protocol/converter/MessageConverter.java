package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.common.ConfigurationLoader;
import com.github.kd.common.KdConfiguration;
import com.github.kd.protocol.Message;

public abstract class MessageConverter implements Converter {

	private KdConfiguration kdProperties = ConfigurationLoader.getInstance().getKdProperties();

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.put(message.getType().id()); // 1 byte
		buffer.put(message.getCommand().id()); // 1 byte
		buffer.flip();
		return buffer;
	}

	protected KdConfiguration getKdProperties() {
		return kdProperties;
	}
}
