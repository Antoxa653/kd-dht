package com.github.kd.protocol;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;

public class FindValueReplyMessage implements Message {

	private MessageType type;
	private MessageCommand command;
	private byte[] senderId;
	private byte[] echoedRandomId;
	private byte[] key;
	private List<Value> values;
	private byte[] randomId;

	public FindValueReplyMessage(MessageType type, MessageCommand command, byte[] senderId, byte[] echoedRandomId,
			byte[] key, List<Value> values, byte[] randomId) {

		this.type = type;
		this.command = command;
		this.senderId = senderId;
		this.echoedRandomId = echoedRandomId;
		this.key = key;
		this.values = values;
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

		return 2 + senderId.length + echoedRandomId.length + key.length + valuesTotalLenght() + randomId.length;
	}

	public byte[] getSenderId() {
		return senderId;
	}

	public byte[] getEchoedRandomId() {
		return echoedRandomId;
	}

	public byte[] getKey() {
		return key;
	}

	public List<Value> getValues() {
		return values;
	}

	public byte[] getRandomId() {
		return randomId;
	}

	private int valuesTotalLenght() {
		return 4 + values.size() * Value.LENGTH; // 4 bytes for specifying a number of values
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", type)
				.add("command", command)
				.add("senderId", Arrays.toString(senderId))
				.add("echoedRandomId", Arrays.toString(echoedRandomId))
				.add("values", values)
				.add("randomId", Arrays.toString(randomId))
				.toString();
	}

}
