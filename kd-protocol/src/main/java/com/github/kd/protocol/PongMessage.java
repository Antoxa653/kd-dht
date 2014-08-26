package com.github.kd.protocol;

import java.util.Arrays;

import com.google.common.base.Objects;

public class PongMessage implements Message {

	private MessageType type;

	private MessageCommand command;

	private byte[] senderId;

	private byte[] echoedRandomId;

	public PongMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] echoedRandomId) {
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.echoedRandomId = echoedRandomId;
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
		return 2 + senderId.length + echoedRandomId.length;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getEchoedRandomId() {
		return echoedRandomId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", type)
				.add("command", command)
				.add("senderId", Arrays.toString(senderId))
				.add("echoedRandomId", Arrays.toString(echoedRandomId))
				.toString();
	}
}
