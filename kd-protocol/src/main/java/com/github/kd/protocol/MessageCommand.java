package com.github.kd.protocol;

public enum MessageCommand {

	PING((byte) 1),
	PING_PONG((byte) 2),
	PING_PONG_PING((byte) 3);

	private byte id;

	private MessageCommand(byte id) {
		this.id = id;
	}

	public byte id() {
		return id;
	}

	public static MessageCommand valueOf(byte id) {
		switch (id) {
		case 1:
			return PING;
		case 2:
			return PING_PONG;
		case 3:
			return PING_PONG_PING;
		default:
			throw new IllegalArgumentException("No message command with such an id: " + id);
		}
	}
}
