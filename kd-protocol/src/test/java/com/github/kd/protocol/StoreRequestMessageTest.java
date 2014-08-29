package com.github.kd.protocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class StoreRequestMessageTest {

	private MessageType type = MessageType.REQUEST;

	private MessageCommand command = MessageCommand.STORE;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] key = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	private List<Value> values = mockValues();

	@Test
	public void testSerialization() {
		StoreRequestMessage message = new StoreRequestMessage(type, command, senderId, key,
				values);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, key,
				values);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		StoreRequestMessage expectedMessage = new StoreRequestMessage(type, command, senderId, key,
				values);
		StoreRequestMessage actualMessage = (StoreRequestMessage) ConverterUtils.deserialize(createSerializedMessage(
				type, command, senderId, key, values));

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getKey(), actualMessage.getKey());

		List<Value> expectedContacts = expectedMessage.getValues();
		List<Value> actualContacts = actualMessage.getValues();

		Assert.assertEquals(2, actualContacts.size());
		Assert.assertEquals(expectedContacts.get(0), actualContacts.get(0));
		Assert.assertEquals(expectedContacts.get(1), actualContacts.get(1));
	}

	@Test
	public void testSerDeserMessage() {
		StoreRequestMessage initialMessage = new StoreRequestMessage(type, command, senderId, key,
				values);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		StoreRequestMessage deserializedMessage = (StoreRequestMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getKey(), deserializedMessage.getKey());

		List<Value> initialContacts = initialMessage.getValues();
		List<Value> deserializedContacts = deserializedMessage.getValues();

		Assert.assertEquals(2, deserializedContacts.size());
		Assert.assertEquals(initialContacts.get(0), deserializedContacts.get(0));
		Assert.assertEquals(initialContacts.get(1), deserializedContacts.get(1));
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] key, List<Value> values) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + key.length + 4
				+ values.size() * Value.LENGTH);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(key);
		buffer.putInt(values.size());

		for (Value value : values) {
			buffer.put(value.getIp());
			buffer.putShort(value.getPort());
		}
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

		return values;
	}

}
