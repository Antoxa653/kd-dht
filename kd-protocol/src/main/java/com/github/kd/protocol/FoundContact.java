package com.github.kd.protocol;

public class FoundContact {
	private byte[] id;
	private byte[] ip;
	private short port;

	public FoundContact(byte[] id, byte[] ip, short port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}

	public byte[] getIp() {
		return ip;
	}

	public byte[] getId() {
		return id;
	}

	public short getPort() {
		return port;
	}
}