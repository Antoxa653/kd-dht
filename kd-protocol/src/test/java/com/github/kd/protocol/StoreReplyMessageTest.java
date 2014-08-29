package com.github.kd.protocol;

import java.nio.ByteBuffer;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class StoreReplyMessageTest {

	private MessageType type = MessageType.REPLY;

	private MessageCommand command = MessageCommand.STORE;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedRandomId = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	private StoreStatusMessage statusMessage = StoreStatusMessage.ACCEPT;

	private byte[] randomId = { 2, 3, 5, 1, 4, 9, 8, 2, 1, 5, 6, 7, 7, 7, 1, 2, 0, 2, 3, 6 };

	@Test
	public void testSerialization() {
		StoreReplyMessage message = new StoreReplyMessage(type, command, senderId, echoedRandomId,
				statusMessage, randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, echoedRandomId,
				statusMessage, randomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		StoreReplyMessage expectedMessage = new StoreReplyMessage(type, command, senderId, echoedRandomId,
				statusMessage, randomId);
		StoreReplyMessage actualMessage = (StoreReplyMessage) ConverterUtils.deserialize(createSerializedMessage(
				type, command, senderId, echoedRandomId,
				statusMessage, randomId));

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getEchoedRandomId(), actualMessage.getEchoedRandomId());
		Assert.assertEquals(expectedMessage.getStatusMessage(), actualMessage.getStatusMessage());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());
	}

	@Test
	public void testSerDeserMessage() {
		StoreReplyMessage initialMessage = new StoreReplyMessage(type, command, senderId, echoedRandomId,
				statusMessage, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		StoreReplyMessage deserializedMessage = (StoreReplyMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedRandomId(), deserializedMessage.getEchoedRandomId());
		Assert.assertEquals(initialMessage.getStatusMessage(), deserializedMessage.getStatusMessage());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId, StoreStatusMessage statusMessage, byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + echoedRandomId.length + 1 + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(echoedRandomId);
		buffer.put(statusMessage.id());
		buffer.put(randomId);

		buffer.flip();
		return buffer;
	}

}
