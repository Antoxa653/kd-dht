package com.github.kd.protocol;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;

public class StoreRequestMessage implements Message {

	private MessageType type;
	private MessageCommand command;
	private byte[] senderId;
	private byte[] key;
	private List<Value> values;

	public StoreRequestMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] key, List<Value> values) {
		super();
		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.key = key;
		this.values = values;
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
		return 2 + senderId.length + key.length + valuesTotalLenght();
	}

	private int valuesTotalLenght() {
		return 4 + values.size() * Value.LENGTH;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getKey() {
		return key;
	}

	public List<Value> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("type", type).add("command", command)
				.add("senderId", Arrays.toString(senderId)).add("key", Arrays.toString(key)).add("values", values)
				.toString();
	}

}
