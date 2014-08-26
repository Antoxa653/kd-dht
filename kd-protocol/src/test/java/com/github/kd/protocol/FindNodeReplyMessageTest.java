package com.github.kd.protocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.common.KdConfigurationLoader;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.converter.ConverterUtils;

public class FindNodeReplyMessageTest {

	private MessageType type = MessageType.REPLY;

	private MessageCommand command = MessageCommand.FIND_NODE_REPLY;

	private byte[] messageId = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	private byte[] randomId = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	private List<FoundContact> contacts = findContact();

	@Test
	public void testSerialization() {
		FindNodeReplyMessage message = new FindNodeReplyMessage(type, command, messageId, senderId, echoedId, randomId,
				contacts);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, messageId, senderId, echoedId,
				randomId, contacts);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		FindNodeReplyMessage actualMessage = (FindNodeReplyMessage) ConverterUtils
				.deserialize(createSerializedMessage(type,
						command, messageId,
						senderId, echoedId, randomId, contacts));
		FindNodeReplyMessage expectedMessage = new FindNodeReplyMessage(type,
				command, messageId,
				senderId, echoedId, randomId, contacts);

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getMessageId(), actualMessage.getMessageId());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getEchoedId(), actualMessage.getEchoedId());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());

		int j = 0;
		for (int i = 0; i < actualMessage.getContacts().size(); i++) {
			for (; j < expectedMessage.getContacts().size();) {
				Assert.assertArrayEquals(actualMessage.getContacts().get(i).getId(),
						expectedMessage.getContacts().get(i).getId());
				Assert.assertArrayEquals(actualMessage.getContacts().get(i).getIp(),
						expectedMessage.getContacts().get(i).getIp());
				Assert.assertEquals(actualMessage.getContacts().get(i).getPort(), expectedMessage.getContacts()
						.get(i).getPort());
				j++;
				break;
			}
		}
	}

	@Test
	public void testSerDeserMessage() {
		FindNodeReplyMessage initialMessage = new FindNodeReplyMessage(type,
				command, messageId,
				senderId, echoedId, randomId, contacts);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		FindNodeReplyMessage deserializedMessage = (FindNodeReplyMessage) ConverterUtils
				.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getMessageId(), deserializedMessage.getMessageId());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedId(), deserializedMessage.getEchoedId());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());

		int j = 0;
		for (int i = 0; i < initialMessage.getContacts().size(); i++) {
			for (; j < deserializedMessage.getContacts().size();) {
				Assert.assertArrayEquals(initialMessage.getContacts().get(i).getId(),
						deserializedMessage.getContacts().get(i).getId());
				Assert.assertArrayEquals(initialMessage.getContacts().get(i).getIp(),
						deserializedMessage.getContacts().get(i).getIp());
				Assert.assertEquals(initialMessage.getContacts().get(i).getPort(), deserializedMessage
						.getContacts()
						.get(i).getPort());
				j++;
				break;
			}
		}
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] messageId,
			byte[] senderId, byte[] echoedId, byte[] randomId, List<FoundContact> contacts) {
		ByteBuffer buffer = ByteBuffer
				.allocate(2
						+ messageId.length
						+ senderId.length
						+ echoedId.length
						+ randomId.length
						+ (KdConfigurationLoader.getInstance().getKdProperties()
								.getValue(KdConstants.KEY_SIZE_PROPERTY) * 2 + 2)
						* contacts.size());
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(messageId);
		buffer.put(senderId);
		buffer.put(echoedId);
		buffer.put(randomId);
		for (FoundContact contact : contacts) {
			buffer.put(contact.getId());
			buffer.put(contact.getIp());
			buffer.putShort(contact.getPort());
		}
		buffer.flip();
		return buffer;
	}

	private ArrayList<FoundContact> findContact() {
		ArrayList<FoundContact> contacts = new ArrayList<FoundContact>();
		byte[] id1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0 };
		byte[] id2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1 };
		byte[] id3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 2 };
		byte[] ip1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 0 };
		byte[] ip2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1 };
		byte[] ip3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2 };
		short port1 = (short) 1;
		short port2 = (short) 2;
		short port3 = (short) 3;
		contacts.add(new FoundContact(id1, ip1, port1));
		contacts.add(new FoundContact(id2, ip2, port2));
		contacts.add(new FoundContact(id3, ip3, port3));
		return contacts;
	}

}
