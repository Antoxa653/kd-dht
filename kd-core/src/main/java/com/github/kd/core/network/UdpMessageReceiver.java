package com.github.kd.core.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpMessageReceiver extends AbstractServer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final int PACKET_BUFFER_SIZE = 1024;

	private static final int TIMEOUT = 5000; // ms

	private DatagramSocket udpSocket;

	public UdpMessageReceiver(int port) throws IOException {
		if (port < 0) {
			throw new IllegalArgumentException("Port value must be a positive number: " + port);
		}
		try {
			udpSocket = new DatagramSocket(port);
			udpSocket.setSoTimeout(TIMEOUT);
		} catch (IOException e) {
			throw new IOException("Could not listen on port: " + port, e);
		}
	}

	@Override
	public void run() {
		while (!udpSocket.isClosed()) {
			try {
				byte[] buffer = new byte[PACKET_BUFFER_SIZE];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				udpSocket.receive(packet);
				execute(new RawMessageHandler(packet));
			} catch (IOException e) {
				logger.warn("Failed to receive a new packet: {}", e.getMessage());
			}
		}
	}

	@Override
	public void shutdown() {
		try {
			logger.debug("Closing udp socket bind to {}", udpSocket.getInetAddress());
			udpSocket.close();
		} finally {
			super.shutdown();
		}
	}
}
