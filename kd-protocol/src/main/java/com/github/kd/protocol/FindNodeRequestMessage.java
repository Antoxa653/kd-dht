package com.github.kd.protocol;

import java.util.Arrays;

import com.google.common.base.Objects;

public class FindNodeRequestMessage implements Message {

	private MessageType type;

	private MessageCommand command;

	private byte[] senderId;

	private byte[] lookUpId;

	private byte[] randomId;

	public FindNodeRequestMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] lookUpId,
			byte[] randomId) {
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.lookUpId = lookUpId;
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
		return 2 + senderId.length + lookUpId.length + randomId.length;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getLookUpId() {
		return lookUpId;
	}

	public byte[] getRandomId() {
		return randomId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", type)
				.add("command", command)
				.add("senderId", Arrays.toString(senderId))
				.add("lookUpId", Arrays.toString(lookUpId))
				.add("randomId", Arrays.toString(randomId))
				.toString();
	}
}
