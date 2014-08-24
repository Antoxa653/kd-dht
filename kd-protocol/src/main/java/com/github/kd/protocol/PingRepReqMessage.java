package com.github.kd.protocol;

public class PingRepReqMessage implements Message {

	private MessageType type;

	private MessageCommand command;

	private byte[] senderId;

	private byte[] echoedRandomId;

	private byte[] randomId;

	public PingRepReqMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] echoedRandomId,
			byte[] randomId) {
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.echoedRandomId = echoedRandomId;
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
		return 2 + senderId.length + echoedRandomId.length + randomId.length;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getEchoedRandomId() {
		return echoedRandomId;
	}

	public byte[] getRandomId() {
		return randomId;
	}	

	public void setSenderId(byte[] senderId) {
		this.senderId = senderId;
	}
}
