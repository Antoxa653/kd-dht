package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.FindValueReplyMessage;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.Value;

public class FindValueReplyMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		FindValueReplyMessage findValueMessage = (FindValueReplyMessage) message;
		buffer.put(findValueMessage.getSenderId());
		buffer.put(findValueMessage.getEchoedRandomId());
		buffer.put(findValueMessage.getKey());
		List<Value> values = findValueMessage.getValues();
		buffer.putInt(values.size());

		for (Value value : values) {
			buffer.put(value.getIp());
			buffer.putShort(value.getPort());
		}
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
		byte[] echoedRandomId = new byte[idSize];
		buffer.get(echoedRandomId);
		byte[] key = new byte[idSize];
		buffer.get(key);

		int valuesNumber = buffer.getInt();
		List<Value> values = new ArrayList<>();

		for (int i = 0; i < valuesNumber; i++) {
			byte[] ip = new byte[4]; // IP address takes 4 bytes
			buffer.get(ip);
			short port = buffer.getShort();
			values.add(new Value(ip, port));
		}
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		FindValueReplyMessage message = new FindValueReplyMessage(type, command, senderId, echoedRandomId, key, values,
				randomId);
		return message;
	}

}
