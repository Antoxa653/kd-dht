package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.Map;

import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;

public final class ConverterUtils {

	private static final Map<MessageType, Map<MessageCommand, Converter>> CONVERTERS_BY_TYPE = new EnumMap<>(
			MessageType.class);

	private static final Map<MessageCommand, Converter> REQUEST_MESSAGE_CONVERTERS = new EnumMap<>(
			MessageCommand.class);

	private static final Map<MessageCommand, Converter> REPLY_MESSAGE_CONVERTERS = new EnumMap<>(
			MessageCommand.class);

	private static final int MESSAGE_TYPE_BYTE_INDEX = 0;

	private static final int MESSAGE_COMMAND_BYTE_INDEX = 1;

	static {
		CONVERTERS_BY_TYPE.put(MessageType.REQUEST, REQUEST_MESSAGE_CONVERTERS);
		CONVERTERS_BY_TYPE.put(MessageType.REPLY, REPLY_MESSAGE_CONVERTERS);

		REQUEST_MESSAGE_CONVERTERS.put(MessageCommand.PING, new PingMessageConverter());
		REQUEST_MESSAGE_CONVERTERS.put(MessageCommand.PING_PONG, new PingPongMessageConverter());
		REQUEST_MESSAGE_CONVERTERS.put(MessageCommand.FIND_NODE, new FindNodeRequestMessageConverter());
		REQUEST_MESSAGE_CONVERTERS.put(MessageCommand.FIND_VALUE, new FindValueRequestMessageConverter());

		REPLY_MESSAGE_CONVERTERS.put(MessageCommand.PONG, new PongMessageConverter());
		REPLY_MESSAGE_CONVERTERS.put(MessageCommand.FIND_NODE, new FindNodeReplyMessageConverter());
		REPLY_MESSAGE_CONVERTERS.put(MessageCommand.FIND_VALUE, new FindValueReplyMessageConverter());
	}

	private ConverterUtils() {
		throw new IllegalStateException("The constructor of the utils class mustn't be called");
	}

	public static ByteBuffer serialize(Message message) {
		Map<MessageCommand, Converter> converters = CONVERTERS_BY_TYPE.get(message.getType());
		Converter converter = converters.get(message.getCommand());
		return converter.serialize(message);
	}

	public static Message deserialize(ByteBuffer buffer) {
		byte type = buffer.get(MESSAGE_TYPE_BYTE_INDEX);
		Map<MessageCommand, Converter> converters = CONVERTERS_BY_TYPE.get(MessageType.valueOf(type));
		byte command = buffer.get(MESSAGE_COMMAND_BYTE_INDEX);
		Converter converter = converters.get(MessageCommand.valueOf(command));
		return converter.deserialize(buffer);
	}
}
