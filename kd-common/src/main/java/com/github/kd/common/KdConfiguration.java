package com.github.kd.common;

import java.io.File;

public final class KdConfiguration {

	private static final String PROPERTIES_FILE_PATH = "src/main/resources/kd.props";

	private static final File PROPERTIES_FILE = new File(PROPERTIES_FILE_PATH);

	private static KdConfiguration instance;

	private final KdProperties kdProperties;

	private KdConfiguration() {
		kdProperties = KdProperties.load(PROPERTIES_FILE);
	}

	public static int get(String propertyKey) {
		if (instance == null) {
			instance = new KdConfiguration();
		}
		return instance.kdProperties.getValue(propertyKey);
	}
}
