package com.github.kd.core.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KdSocketMessageHandler implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Socket socket;

	public KdSocketMessageHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedInputStream inputStream = null;

		try {
			inputStream = new BufferedInputStream(socket.getInputStream());
			int data;

			while ((data = inputStream.read()) != -1) {

			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException occurred while closing input stream", e);
				}
			}
			shutdown();
		}
	}

	private void shutdown() {
		try {
			socket.close();
			logger.debug("Socket was closed");
		} catch (IOException e) {
			logger.error("IOException occurred while closing the socket", e);
		}
	}
}
