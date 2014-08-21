package com.github.kd.core.network;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kd.protocol.Message;
import com.github.kd.protocol.converter.ConverterUtils;

public class RawMessageHandler implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final DatagramPacket packet;

	public RawMessageHandler(DatagramPacket packet) {
		this.packet = packet;
	}

	@Override
	public void run() {
		Message message = ConverterUtils.deserialize(ByteBuffer.wrap(packet.getData()));

		switch (message.getType()) {
		case REQUEST:
			processRequest(message);
		case REPLY:
			processReply(message);
		default:
			throw new IllegalStateException("No such a message type: " + message.getType());
		}
	}

	private void processRequest(Message message) {

	}

	private void processReply(Message message) {

	}
}
