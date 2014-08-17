package com.github.kd.common;

import java.io.File;

public final class KdConfigurationLoader {

	private static final String PROPERTIES_FILE_PATH = "src/main/resources/kd.props";

	private static final File PROPERTIES_FILE = new File(PROPERTIES_FILE_PATH);

	private static KdConfigurationLoader instance;

	private final KdProperties kdProperties;

	private KdConfigurationLoader() {
		kdProperties = KdProperties.load(PROPERTIES_FILE);
	}

	public static KdConfigurationLoader getInstance() {
		if (instance == null) {
			instance = new KdConfigurationLoader();
		}
		return instance;
	}

	public KdProperties getKdProperties() {
		return kdProperties;
	}
}
