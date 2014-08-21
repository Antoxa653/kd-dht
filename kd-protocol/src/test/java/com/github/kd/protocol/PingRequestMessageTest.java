package com.github.kd.protocol;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;
import com.github.kd.protocol.PingRequestMessage;
import com.github.kd.protocol.converter.ConverterUtils;

public class PingRequestMessageTest {

	private MessageType type = MessageType.REQUEST;

	private MessageCommand command = MessageCommand.PING;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] randomId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	@Test
	public void testSerialization() {
		PingRequestMessage message = new PingRequestMessage(type, command, senderId, randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, randomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		PingRequestMessage actualMessage = (PingRequestMessage) ConverterUtils.deserialize(createSerializedMessage(
				type, command, senderId,
				randomId));
		PingRequestMessage expectedMessage = new PingRequestMessage(type, command, senderId, randomId);

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());
	}

	@Test
	public void testSerDeserMessage() {
		PingRequestMessage initialMessage = new PingRequestMessage(type, command, senderId, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		PingRequestMessage deserializedMessage = (PingRequestMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(randomId);
		buffer.flip();
		return buffer;
	}
}
