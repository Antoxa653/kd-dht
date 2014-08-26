package com.github.kd.protocol;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class PongMessageTest {

	private MessageType type = MessageType.REPLY;

	private MessageCommand command = MessageCommand.PONG;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedRandomId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	@Test
	public void testSerialization() {
		PongMessage message = new PongMessage(type, command, senderId, echoedRandomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, echoedRandomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		PongMessage actualMessage = (PongMessage) ConverterUtils.deserialize(createSerializedMessage(type,
				command, senderId,
				echoedRandomId));
		PongMessage expectedMessage = new PongMessage(type, command, senderId, echoedRandomId);

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getEchoedRandomId(), actualMessage.getEchoedRandomId());
	}

	@Test
	public void testSerDeserMessage() {
		PongMessage initialMessage = new PongMessage(type, command, senderId, echoedRandomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		PongMessage deserializedMessage = (PongMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedRandomId(), deserializedMessage.getEchoedRandomId());
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + echoedRandomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(echoedRandomId);
		buffer.flip();
		return buffer;
	}
}
