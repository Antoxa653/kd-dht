package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;

import com.github.kd.protocol.Message;

public interface Converter {

	ByteBuffer serialize(Message message);

	Message deserialize(ByteBuffer buffer);
}
