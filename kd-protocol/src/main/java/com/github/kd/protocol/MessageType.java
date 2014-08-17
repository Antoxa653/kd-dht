package com.github.kd.protocol;

public enum MessageType {

	REQUEST((byte) 1),
	REPLY((byte) 2);

	private byte id;

	private MessageType(byte id) {
		this.id = id;
	}

	public byte id() {
		return id;
	}

	public static MessageType valueOf(byte id) {
		switch (id) {
		case 1:
			return REQUEST;
		case 2:
			return REPLY;
		default:
			throw new IllegalArgumentException("No message type with such an id: " + id);
		}
	}
}
