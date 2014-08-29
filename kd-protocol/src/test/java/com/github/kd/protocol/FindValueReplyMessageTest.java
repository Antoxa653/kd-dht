package com.github.kd.protocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class FindValueReplyMessageTest {

	private MessageType type = MessageType.REPLY;

	private MessageCommand command = MessageCommand.FIND_VALUE;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedRandomId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	private byte[] key = { 4, 3, 1, 5, 7, 7, 8, 1, 2, 5, 7, 3, 2, 2, 1, 9, 8, 6, 2, 7 };

	private byte[] randomId = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	private List<Value> values = mockValues();

	@Test
	public void testSerialization() {
		FindValueReplyMessage message = new FindValueReplyMessage(type, command, senderId, echoedRandomId, key, values,
				randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, echoedRandomId, key,
				values, randomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		FindValueReplyMessage expectedMessage = new FindValueReplyMessage(type, command, senderId, echoedRandomId, key,
				values, randomId);
		FindValueReplyMessage actualMessage = (FindValueReplyMessage) ConverterUtils
				.deserialize(createSerializedMessage(
						type, command, senderId, echoedRandomId, key, values, randomId));

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getKey(), actualMessage.getKey());
		Assert.assertArrayEquals(expectedMessage.getEchoedRandomId(), actualMessage.getEchoedRandomId());

		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());

		List<Value> expectedValues = expectedMessage.getValues();
		List<Value> actualValues = actualMessage.getValues();

		Assert.assertEquals(3, actualValues.size());
		Assert.assertEquals(expectedValues.get(0), actualValues.get(0));
		Assert.assertEquals(expectedValues.get(1), actualValues.get(1));
	}

	@Test
	public void testSerDeserMessage() {
		FindValueReplyMessage initialMessage = new FindValueReplyMessage(type, command, senderId, echoedRandomId, key,
				values, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		FindValueReplyMessage deserializedMessage = (FindValueReplyMessage) ConverterUtils
				.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedRandomId(), deserializedMessage.getEchoedRandomId());
		Assert.assertArrayEquals(initialMessage.getKey(), deserializedMessage.getKey());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());

		List<Value> initiaValues = initialMessage.getValues();
		List<Value> deserializedValues = deserializedMessage.getValues();

		Assert.assertEquals(3, deserializedValues.size());
		Assert.assertEquals(initiaValues.get(0), deserializedValues.get(0));
		Assert.assertEquals(initiaValues.get(1), deserializedValues.get(1));
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId, byte[] key, List<Value> values, byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + echoedRandomId.length + key.length + 4
				+ values.size() * Value.LENGTH + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(echoedRandomId);
		buffer.put(key);
		buffer.putInt(values.size());

		for (Value value : values) {
			buffer.put(value.getIp());
			buffer.putShort(value.getPort());
		}
		buffer.put(randomId);
		buffer.flip();
		return buffer;
	}

	private List<Value> mockValues() {
		List<Value> values = new ArrayList<>();

		byte[] ip = { (byte) 172, (byte) 21, (byte) 10, (byte) 137 };
		short port = (short) 7777;
		values.add(new Value(ip, port));

		ip = new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 };
		port = (short) 8714;
		values.add(new Value(ip, port));

		ip = new byte[] { (byte) 201, (byte) 174, (byte) 0, (byte) 1 };
		port = (short) 65535;
		values.add(new Value(ip, port));
		return values;
	}

}
