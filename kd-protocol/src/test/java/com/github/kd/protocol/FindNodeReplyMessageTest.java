package com.github.kd.protocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.kd.protocol.converter.ConverterUtils;

public class FindNodeReplyMessageTest {

	private MessageType type = MessageType.REPLY;

	private MessageCommand command = MessageCommand.FIND_NODE;

	private byte[] senderId = { 3, 2, 4, 5, 5, 6, 7, 8, 7, 1, 7, 8, 3, 7, 2, 7, 1, 0, 9, 2 };

	private byte[] echoedRandomId = { 1, 8, 4, 6, 7, 9, 6, 5, 4, 5, 6, 9, 7, 2, 1, 1, 0, 0, 3, 7 };

	private byte[] randomId = { 2, 4, 5, 1, 2, 9, 9, 5, 3, 5, 6, 7, 7, 8, 1, 9, 0, 2, 3, 3 };

	private List<Contact> contacts = mockContacts();

	@Test
	public void testSerialization() {
		FindNodeReplyMessage message = new FindNodeReplyMessage(type, command, senderId, echoedRandomId, contacts,
				randomId);
		ByteBuffer actualSerializedMessage = ConverterUtils.serialize(message);
		ByteBuffer exceptedSerializedMessage = createSerializedMessage(type, command, senderId, echoedRandomId,
				contacts, randomId);

		Assert.assertArrayEquals(exceptedSerializedMessage.array(), actualSerializedMessage.array());
	}

	@Test
	public void testDeserialization() {
		FindNodeReplyMessage expectedMessage = new FindNodeReplyMessage(type, command, senderId, echoedRandomId,
				contacts, randomId);
		FindNodeReplyMessage actualMessage = (FindNodeReplyMessage) ConverterUtils.deserialize(createSerializedMessage(
				type, command, senderId, echoedRandomId, contacts, randomId));

		Assert.assertEquals(expectedMessage.getType(), actualMessage.getType());
		Assert.assertEquals(expectedMessage.getCommand(), actualMessage.getCommand());
		Assert.assertArrayEquals(expectedMessage.getSenderId(), actualMessage.getSenderId());
		Assert.assertArrayEquals(expectedMessage.getEchoedRandomId(), actualMessage.getEchoedRandomId());
		Assert.assertArrayEquals(expectedMessage.getRandomId(), actualMessage.getRandomId());

		List<Contact> expectedContacts = expectedMessage.getContacts();
		List<Contact> actualContacts = actualMessage.getContacts();

		Assert.assertEquals(3, actualContacts.size());
		Assert.assertEquals(expectedContacts.get(0), actualContacts.get(0));
		Assert.assertEquals(expectedContacts.get(1), actualContacts.get(1));
		Assert.assertEquals(expectedContacts.get(2), actualContacts.get(2));
	}

	@Test
	public void testSerDeserMessage() {
		FindNodeReplyMessage initialMessage = new FindNodeReplyMessage(type, command, senderId, echoedRandomId,
				contacts, randomId);
		ByteBuffer serializedMessage = ConverterUtils.serialize(initialMessage);
		FindNodeReplyMessage deserializedMessage = (FindNodeReplyMessage) ConverterUtils.deserialize(serializedMessage);

		Assert.assertEquals(initialMessage.getType(), deserializedMessage.getType());
		Assert.assertEquals(initialMessage.getCommand(), deserializedMessage.getCommand());
		Assert.assertArrayEquals(initialMessage.getSenderId(), deserializedMessage.getSenderId());
		Assert.assertArrayEquals(initialMessage.getEchoedRandomId(), deserializedMessage.getEchoedRandomId());
		Assert.assertArrayEquals(initialMessage.getRandomId(), deserializedMessage.getRandomId());

		List<Contact> initialContacts = initialMessage.getContacts();
		List<Contact> deserializedContacts = deserializedMessage.getContacts();

		Assert.assertEquals(3, deserializedContacts.size());
		Assert.assertEquals(initialContacts.get(0), deserializedContacts.get(0));
		Assert.assertEquals(initialContacts.get(1), deserializedContacts.get(1));
		Assert.assertEquals(initialContacts.get(2), deserializedContacts.get(2));
	}

	private static ByteBuffer createSerializedMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId, List<Contact> contacts, byte[] randomId) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + senderId.length + echoedRandomId.length + 4
				+ contacts.size() * Contact.LENGTH + randomId.length);
		buffer.put(type.id());
		buffer.put(command.id());
		buffer.put(senderId);
		buffer.put(echoedRandomId);
		buffer.putInt(contacts.size());

		for (Contact contact : contacts) {
			buffer.put(contact.getId());
			buffer.put(contact.getIp());
			buffer.putShort(contact.getPort());
		}
		buffer.put(randomId);
		buffer.flip();
		return buffer;
	}

	private List<Contact> mockContacts() {
		List<Contact> contacts = new ArrayList<>();

		byte[] id = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0 };
		byte[] ip = { (byte) 172, (byte) 21, (byte) 10, (byte) 137 };
		short port = (short) 7777;
		contacts.add(new Contact(id, ip, port));

		id = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1 };
		ip = new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 };
		port = (short) 8714;
		contacts.add(new Contact(id, ip, port));

		id = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 2 };
		ip = new byte[] { (byte) 201, (byte) 174, (byte) 0, (byte) 1 };
		port = (short) 65535;
		contacts.add(new Contact(id, ip, port));
		return contacts;
	}
}
