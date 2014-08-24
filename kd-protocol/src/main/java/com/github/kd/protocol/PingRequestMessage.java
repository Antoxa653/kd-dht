package com.github.kd.protocol;

import com.google.common.base.Objects;

public class PingRequestMessage implements Message {

	private MessageType type;

	private MessageCommand command;

	private byte[] senderId;

	private byte[] randomId;

	public PingRequestMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] randomId) {
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.randomId = randomId;
	}

	@Override
	public int length() {
		return 2 + senderId.length + randomId.length; // 2 = one byte for the type + one byte for the command
	}

	@Override
	public MessageType getType() {
		return type;
	}

	@Override
	public MessageCommand getCommand() {
		return command;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getRandomId() {
		return randomId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", type)
				.add("command", command)
				.toString();
	}
}
