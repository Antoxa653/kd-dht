package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.github.kd.protocol.Contact;
import com.github.kd.protocol.FindNodeReplyMessage;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;

public class FindNodeReplyMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer common = super.serialize(message);
		buffer.put(common);
		FindNodeReplyMessage findNodeMessage = (FindNodeReplyMessage) message;
		buffer.put(findNodeMessage.getSenderId());
		buffer.put(findNodeMessage.getEchoedRandomId());

		List<Contact> contacts = findNodeMessage.getContacts();
		buffer.putInt(contacts.size());

		for (Contact contact : contacts) {
			buffer.put(contact.getId());
			buffer.put(contact.getIp());
			buffer.putShort(contact.getPort());
		}
		buffer.put(findNodeMessage.getRandomId());
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

		int contactsNumber = buffer.getInt();
		List<Contact> contacts = new ArrayList<>();

		for (int i = 0; i < contactsNumber; i++) {
			byte[] id = new byte[idSize];
			buffer.get(id);
			byte[] ip = new byte[4]; // IP address takes 4 bytes
			buffer.get(ip);
			short port = buffer.getShort();
			contacts.add(new Contact(id, ip, port));
		}
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		buffer.clear();
		FindNodeReplyMessage message = new FindNodeReplyMessage(type, command, senderId, echoedRandomId, contacts,
				randomId);
		return message;
	}
}
