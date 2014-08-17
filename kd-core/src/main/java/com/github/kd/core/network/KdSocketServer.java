package com.github.kd.core.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KdSocketServer extends AbstractServer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ServerSocket serverSocket;

	public KdSocketServer(int port) throws IOException {
		if (port < 0) {
			throw new IllegalArgumentException("Port value must be a positive number: " + port);
		}
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new IOException("Could not listen on port: " + port, e);
		}
	}

	@Override
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				logger.debug("Waiting for a new connection");
				final Socket socket = serverSocket.accept();
				socket.setSoTimeout(5000);
				logger.debug("Connection has been accepted from " + socket.getInetAddress().getHostName());
				execute(new KdSocketMessageHandler(socket));
			} catch (IOException e) {
				logger.warn("Failed to accept a new connection: server socket was closed.");
			}
		}
	}

	@Override
	public void shutdown() {
		try {
			serverSocket.close();
			logger.debug("Server was stopped");
		} catch (IOException e) {
			logger.error("IOException occurred while closing server socket:", e);
		} finally {
			super.shutdown();
		}
	}
}
