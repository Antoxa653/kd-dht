package com.github.kd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KdConfiguration {

	private static Logger logger = LoggerFactory.getLogger(KdConfiguration.class);

	private Properties store;

	private KdConfiguration(Properties properties) {
		if (properties.isEmpty()) {
			store = loadDefault();
		} else {
			store = properties;
		}
	}

	public static KdConfiguration load(File configFile) {
		Properties properties = new Properties();

		if (configFile != null && configFile.exists() && configFile.isFile()) {
			try (InputStream configFileStream = new FileInputStream(configFile)) {
				properties.load(configFileStream);
			} catch (IOException e) {
				logger.error("Couldn't load a config file {}. The default configuration will be used instead.",
						configFile.getAbsolutePath(), e);
			}
		}
		return new KdConfiguration(properties);
	}

	public int getValue(String propertyKey) {
		if (propertyKey == null) {
			throw new IllegalArgumentException("Property key mustn't be null");
		}

		if (!store.containsKey(propertyKey)) {
			throw new IllegalArgumentException("No value for such a property key: " + propertyKey);
		}
		int value = Integer.parseInt(store.getProperty(propertyKey));
		return value;
	}

	private Properties loadDefault() {
		Properties properties = new Properties();
		properties.put(KdConstants.NETWORK_PARALLELISM_DEGREE_PROPERTY, "3");
		properties.put(KdConstants.KEY_SIZE_PROPERTY, "160");
		properties.put(KdConstants.BUCKET_SIZE_PROPERTY, "20");
		properties.put(KdConstants.EXPIRATION_TIME_PROPERTY, "86410");
		properties.put(KdConstants.REFRESH_TIME_PROPERTY, "3600");
		properties.put(KdConstants.REPLICATION_TIME_PROPERTY, "3600");
		properties.put(KdConstants.REPUBLICATION_TIME_PROPERTY, "86400");
		return properties;
	}
}
