package com.github.kd.core.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServer implements Server {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public void execute(Runnable runnable) {
		if (runnable != null) {
			try {
				executor.execute(runnable);
			} catch (RejectedExecutionException e) {
				if (!executor.isShutdown()) {
					logger.warn("Connections submission rejected", e);
				}
			}
		}
	}

	@Override
	public void shutdown() {
		try {
			executor.shutdown();

			if (!executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException occurred while shutting down a server executor.", e);
		}
	}
}
