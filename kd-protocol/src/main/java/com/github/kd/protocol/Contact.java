package com.github.kd.protocol;

import java.util.Arrays;

import com.github.kd.common.KdConfiguration;
import com.github.kd.common.KdConstants;
import com.google.common.base.Objects;

public class Contact {

	public static final int LENGTH = KdConfiguration.get(KdConstants.KEY_SIZE_PROPERTY) + 4 + 2;

	private byte[] id;

	private byte[] ip; // 4 bytes

	private short port; // 2 bytes

	public Contact(byte[] id, byte[] ip, short port) {
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

	@Override
	public int hashCode() {
		return Objects.hashCode(id, ip, port);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Contact other = (Contact) obj;

		return port == other.port
				&& Arrays.equals(id, other.id)
				&& Arrays.equals(ip, other.ip);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", Arrays.toString(id))
				.add("ip", Arrays.toString(ip))
				.add("port", port)
				.toString();
	}
}