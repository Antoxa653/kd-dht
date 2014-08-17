package com.github.kd.common;

import java.io.File;

public final class ConfigurationLoader {

	private static final String CONFIG_FILE_PATH = "src/main/resources/kd.props";

	private static final File CONFIG_FILE = new File(CONFIG_FILE_PATH);

	private static ConfigurationLoader instance;

	private final KdConfiguration kdProperties;

	private ConfigurationLoader() {
		kdProperties = KdConfiguration.load(CONFIG_FILE);
	}

	public static ConfigurationLoader getInstance() {
		if (instance == null) {
			instance = new ConfigurationLoader();
		}
		return instance;
	}

	public KdConfiguration getKdProperties() {
		return kdProperties;
	}
}
