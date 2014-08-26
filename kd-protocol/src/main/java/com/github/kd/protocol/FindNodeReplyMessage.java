package com.github.kd.protocol;

import java.util.List;

import com.github.kd.common.KdConfigurationLoader;
import com.github.kd.common.KdConstants;
import com.github.kd.common.KdProperties;

public class FindNodeReplyMessage implements Message {
	private KdProperties kdProperties = KdConfigurationLoader.getInstance().getKdProperties();
	private MessageType type;
	private MessageCommand command;
	private byte[] messageId;
	private byte[] senderId;
	private byte[] echoedId;
	private byte[] randomId;
	private List<FoundContact> contacts;

	public FindNodeReplyMessage(MessageType type, MessageCommand command, byte[] messageId, byte[] senderId,
			byte[] echoedId, byte[] randomId, List<FoundContact> contacts) {
		this.type = type;
		this.command = command;
		this.messageId = messageId;
		this.senderId = senderId;
		this.echoedId = echoedId;
		this.randomId = randomId;
		this.contacts = contacts;
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
		return 2
				+ messageId.length
				+ senderId.length
				+ echoedId.length
				+ randomId.length
				+ (kdProperties.getValue(KdConstants.KEY_SIZE_PROPERTY) * 2 + 2) // FoundContact id + ip + protocol
				* contacts.size(); // number of founded nodes
	}

	public byte[] getMessageId() {
		return messageId;
	}

	public byte[] getEchoedId() {
		return echoedId;
	}

	public List<FoundContact> getContacts() {
		return contacts;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getRandomId() {
		return randomId;
	}	
}
