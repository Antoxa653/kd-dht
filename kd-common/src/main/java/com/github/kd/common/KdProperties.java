package com.github.kd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KdProperties {

	private static Logger logger = LoggerFactory.getLogger(KdProperties.class);

	private Properties store;

	private KdProperties(Properties properties) {
		if (properties.isEmpty()) {
			store = loadDefault();
		} else {
			store = properties;
		}
	}

	public static KdProperties load(File propertiesFile) {
		Properties properties = new Properties();

		if (propertiesFile != null && propertiesFile.exists() && propertiesFile.isFile()) {
			try (InputStream propertiesFileStream = new FileInputStream(propertiesFile)) {
				properties.load(propertiesFileStream);
			} catch (IOException e) {
				logger.error("Couldn't load a properties file {}. The default properties will be used instead.",
						propertiesFile.getAbsolutePath(), e);
			}
		}
		return new KdProperties(properties);
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
		properties.put(KdConstants.NETWORK_DEGREE_PROPERTY, "3");
		properties.put(KdConstants.KEY_SIZE_PROPERTY, "20"); // 160 bits = 20 bytes
		properties.put(KdConstants.BUCKET_SIZE_PROPERTY, "20");
		properties.put(KdConstants.EXPIRATION_TIME_PROPERTY, "86410");
		properties.put(KdConstants.REFRESH_TIME_PROPERTY, "3600");
		properties.put(KdConstants.REPLICATION_TIME_PROPERTY, "3600");
		properties.put(KdConstants.REPUBLICATION_TIME_PROPERTY, "86400");
		return properties;
	}
}
