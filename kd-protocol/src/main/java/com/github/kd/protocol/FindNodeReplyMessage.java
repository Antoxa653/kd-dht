package com.github.kd.protocol;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;

public class FindNodeReplyMessage implements Message {

	private MessageType type;

	private MessageCommand command;

	private byte[] senderId;

	private byte[] echoedRandomId;

	private List<Contact> contacts;

	private byte[] randomId;

	public FindNodeReplyMessage(MessageType type, MessageCommand command, byte[] senderId,
			byte[] echoedRandomId, List<Contact> contacts, byte[] randomId) {
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.echoedRandomId = echoedRandomId;
		this.contacts = contacts;
		this.randomId = randomId;
	}

	@Override
	public MessageType getType() {
		return type;
	}

	@Override
	public MessageCommand getCommand() {
		return command;
	}

	@Override
	public int length() {
		return 2 + senderId.length + echoedRandomId.length + contactsTotalLength() + randomId.length;
	}

	public byte[] getEchoedRandomId() {
		return echoedRandomId;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getRandomId() {
		return randomId;
	}

	private int contactsTotalLength() {
		return 4 + contacts.size() * Contact.LENGTH; // 4 bytes for specifying a number of contacts
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", type)
				.add("command", command)
				.add("senderId", Arrays.toString(senderId))
				.add("echoedRandomId", Arrays.toString(echoedRandomId))
				.add("contacts", contacts)
				.add("randomId", Arrays.toString(randomId))
				.toString();
	}
}
