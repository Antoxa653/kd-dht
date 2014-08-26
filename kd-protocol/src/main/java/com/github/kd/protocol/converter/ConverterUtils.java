package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.Map;

import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;

public final class ConverterUtils {

	private static final Map<MessageCommand, Converter> MESSAGE_CONVERTERS = new EnumMap<>(MessageCommand.class);

	private static final int MESSAGE_COMMAND_BYTE_INDEX = 1;

	static {
		MESSAGE_CONVERTERS.put(MessageCommand.PING, new PingRequestMessageConverter());		
		MESSAGE_CONVERTERS.put(MessageCommand.FIND_NODE_REQUEST, new FindNodeRequestMessageConverter());
		MESSAGE_CONVERTERS.put(MessageCommand.FIND_NODE_REPLY, new FindNodeReplyMessageConverter());

	}

	private ConverterUtils() {
		throw new IllegalStateException("The constructor of the utils class mustn't be called");
	}

	public static ByteBuffer serialize(Message message) {
		Converter converter = MESSAGE_CONVERTERS.get(message.getCommand());
		return converter.serialize(message);
	}

	public static Message deserialize(ByteBuffer buffer) {
		byte command = buffer.get(MESSAGE_COMMAND_BYTE_INDEX);
		Converter converter = MESSAGE_CONVERTERS.get(MessageCommand.valueOf(command));
		return converter.deserialize(buffer);
	}
}
