package com.github.kd.protocol;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class PingRepReqMessageTest {

	private MessageType type = MessageType.REQUEST;

	private MessageCommand command = MessageCommand.PING_PONG_PING;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedRandomId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	private byte[] randomId = { 2, 2, 8, 3, 2, 2, 1, 5, 6, 3, 8, 0, 6, 5, 4, 3, 2, 2, 1, 3 };

	@Test
	public void testSerialization() {
		PingRepReqMessage message = new PingRepReqMessage(type, command, senderId, echoedRandomId, randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, echoedRandomId,
				randomId);
		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		PingRepReqMessage actualMessage = (PingRepReqMessage) ConverterUtils.deserialize(createSerializedMessage(type,
				command, senderId,
				echoedRandomId, randomId));
		PingRepReqMessage expectedMessage = new PingRepReqMessage(type, command, senderId,
				echoedRandomId, randomId);

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getEchoedRandomId(), actualMessage.getEchoedRandomId());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());
	}

	@Test
	public void testSerDeserMessage() {
		PingRepReqMessage initialMessage = new PingRepReqMessage(type, command, senderId, echoedRandomId, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		PingRepReqMessage deserializedMessage = (PingRepReqMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedRandomId(), deserializedMessage.getEchoedRandomId());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId,
			byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + echoedRandomId.length + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(echoedRandomId);
		buffer.put(randomId);
		buffer.flip();
		return buffer;
	}

}
