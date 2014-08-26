package com.github.kd.protocol;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class FindNodeRequestMessageTest {

	private MessageType type = MessageType.REQUEST;

	private MessageCommand command = MessageCommand.FIND_NODE_REQUEST;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] lookUpId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	private byte[] randomId = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	@Test
	public void testSerialization() {
		FindNodeRequestMessage message = new FindNodeRequestMessage(type, command, senderId, lookUpId, randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, lookUpId, randomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		FindNodeRequestMessage actualMessage = (FindNodeRequestMessage) ConverterUtils
				.deserialize(createSerializedMessage(type,
						command, senderId,
						lookUpId, randomId));
		FindNodeRequestMessage expectedMessage = new FindNodeRequestMessage(type, command, senderId, lookUpId, randomId);

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getLookUpId(), actualMessage.getLookUpId());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());
	}

	@Test
	public void testSerDeserMessage() {
		FindNodeRequestMessage initialMessage = new FindNodeRequestMessage(type, command, senderId, lookUpId, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		FindNodeRequestMessage deserializedMessage = (FindNodeRequestMessage) ConverterUtils
				.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getLookUpId(), deserializedMessage.getLookUpId());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] lookUpId, byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + lookUpId.length + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(lookUpId);
		buffer.put(randomId);
		buffer.flip();
		return buffer;
	}

}
