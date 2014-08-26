package com.github.kd.protocol.converter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.kd.common.KdConstants;
import com.github.kd.protocol.FindNodeReplyMessage;
import com.github.kd.protocol.FoundContact;
import com.github.kd.protocol.Message;
import com.github.kd.protocol.MessageCommand;
import com.github.kd.protocol.MessageType;

public class FindNodeReplyMessageConverter extends MessageConverter {

	@Override
	public ByteBuffer serialize(Message message) {
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		ByteBuffer type = super.serialize(message);
		buffer.put(type);
		FindNodeReplyMessage findNodeReply = (FindNodeReplyMessage) message;
		buffer.put(findNodeReply.getMessageId());
		buffer.put(findNodeReply.getSenderId());
		buffer.put(findNodeReply.getEchoedId());
		buffer.put(findNodeReply.getRandomId());

		for (FoundContact contact : findNodeReply.getContacts()) {
			buffer.put(contact.getId());
			buffer.put(contact.getIp());
			buffer.putShort(contact.getPort());
		}
		buffer.flip();
		return buffer;
	}

	@Override
	public Message deserialize(ByteBuffer buffer) {
		MessageType type = MessageType.valueOf(buffer.get());
		MessageCommand command = MessageCommand.valueOf(buffer.get());
		int idSize = getKdProperties().getValue(KdConstants.KEY_SIZE_PROPERTY);
		byte[] messageId = new byte[idSize];
		buffer.get(messageId);
		byte[] senderId = new byte[idSize];
		buffer.get(senderId);
		byte[] echoedId = new byte[idSize];
		buffer.get(echoedId);
		byte[] randomId = new byte[idSize];
		buffer.get(randomId);
		List<FoundContact> contacts = new ArrayList<FoundContact>();
		while (buffer.hasRemaining()) {
			byte[] id = new byte[idSize];
			buffer.get(id);
			byte[] ip = new byte[idSize];
			buffer.get(ip);
			short port = buffer.getShort();
			contacts.add(new FoundContact(id, ip, port));
		}
		buffer.clear();
		FindNodeReplyMessage message = new FindNodeReplyMessage(type, command, messageId, senderId, echoedId, randomId,
				contacts);
		return message;
	}

}
