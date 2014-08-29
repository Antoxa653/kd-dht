package com.github.kd.protocol;

import java.util.Arrays;

import com.google.common.base.Objects;

public class StoreReplyMessage implements Message {

	private MessageType type;
	private MessageCommand command;
	private byte[] senderId;
	private byte[] echoedRandomId;
	private byte statusMessage;
	private byte[] randomId;

	public StoreReplyMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] echoedRandomId,
			StoreStatusMessage statusMessage, byte[] randomId) {
		super();
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.echoedRandomId = echoedRandomId;
		this.statusMessage = statusMessage.id();
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
		return 2 + senderId.length + echoedRandomId.length + 1 + randomId.length; //statusMessage.lenght = 1 byte
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getEchoedRandomId() {
		return echoedRandomId;
	}

	public byte getStatusMessage() {
		return statusMessage;
	}

	public byte[] getRandomId() {
		return randomId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("type", type).add("command", command)
				.add("senderId", Arrays.toString(senderId)).add("echoedRandomId", Arrays.toString(echoedRandomId))
				.add("statusMessage", StoreStatusMessage.valueOf(statusMessage))
				.add("randomId", Arrays.toString(randomId))
				.toString();
	}

}
