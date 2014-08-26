package com.github.kd.protocol;

public enum MessageCommand {

	PING((byte) 1),
	FIND_NODE_REQUEST((byte) 2),
	FIND_NODE_REPLY((byte) 3);

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
			return FIND_NODE_REQUEST;
		case 3:
			return FIND_NODE_REPLY;
		default:
			throw new IllegalArgumentException("No message command with such an id: " + id);
		}
	}
}
