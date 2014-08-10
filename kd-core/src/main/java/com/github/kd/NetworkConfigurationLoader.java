package com.github.kd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkConfigurationLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public HashMap<String, String> load() {
		Properties prop = new Properties();
		HashMap<String, String> propertiesList = new HashMap<String, String>();
		String fileName = "src/main/resources/kd.props";
		File file = new File(fileName);
		if (file.exists()) {
			try {
				InputStream is = new FileInputStream(fileName);
				prop.load(is);
				Enumeration<?> e = prop.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = prop.getProperty(key);
					propertiesList.put(key.toUpperCase(), value);
				}
			} catch (FileNotFoundException e1) {
				logger.error("file src/main/resources/kd.props was not found, default network configuration parameters set!");
			} catch (IOException e) {
				logger.error("IOException occurs while reading src/main/resources/kd.props file, default network configuration parameters set!");
			}
			return propertiesList;
		}
		else {
			for (NetworkProperties props : NetworkProperties.values()) {
				propertiesList.put(props.toString(), props.getDefaultValue());
			}

			return propertiesList;
		}

	}

	public static void main(String[] args) {
		NetworkConfigurationLoader n = new NetworkConfigurationLoader();
		System.out.println(n.load().toString());
	}
}
