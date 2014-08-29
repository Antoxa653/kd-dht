package com.github.kd.protocol;

import java.util.Arrays;

import com.google.common.base.Objects;

public class Value {

	public static final int LENGTH = 6; //4 bytes ip + 2 bytes port

	private byte[] ip; // 4 bytes
	private short port; // 2 bytes

	public Value(byte[] ip, short port) {
		this.ip = ip;
		this.port = port;
	}

	public byte[] getIp() {
		return ip;
	}

	public short getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(ip, port);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Value other = (Value) obj;
		if (!Arrays.equals(ip, other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
