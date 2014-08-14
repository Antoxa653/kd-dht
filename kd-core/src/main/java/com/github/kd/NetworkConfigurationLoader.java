package com.github.kd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkConfigurationLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public Properties load() {
		Properties properties = new Properties();
		String fileName = "src/main/resources/kd.props";
		File file = new File(fileName);
		if (file.exists()) {
			try {
				InputStream is = new FileInputStream(fileName);
				properties.load(is);
			} catch (FileNotFoundException e1) {
				logger.error("file src/main/resources/kd.props was not found");
			} catch (IOException e) {
				logger.error("IOException occurs while reading src/main/resources/kd.props file");
			}
			return properties;
		}
		else {
			for (NetworkProperties props : NetworkProperties.values()) {
				properties.put(props.getKey(), props.getValue());
			}
			return properties;
		}
	}
}
