package com.github.kd.protocol;

public enum StoreStatusMessage {
	ACCEPT((byte) 1),
	CANCEL((byte) 2);

	private byte id;

	private StoreStatusMessage(byte id) {
		this.id = id;
	}

	public byte id() {
		return id;
	}

	public static StoreStatusMessage valueOf(byte id) {		
		switch (id) {
		case 1:
			return ACCEPT;
		case 2:
			return CANCEL;
		default:
			throw new IllegalArgumentException("No message type with such an id: " + id);
		}
	}

}
