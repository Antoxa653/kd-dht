package com.github.kd;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkConfigurationLoaderTest {
	private static Properties properties;
	private static Properties defaultProperties;
	private static String fileName = "src/main/resources/kd.props";

	@BeforeClass
	public static void init() throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			properties = new Properties();
			//init network properties form kd.props
			InputStream is = new FileInputStream(fileName);
			properties.load(is);
		}
		else {
			//init network default properties 
			defaultProperties = new Properties();
			for (NetworkProperties props : NetworkProperties.values()) {
				defaultProperties.put(props.getKey(), props.getValue());
			}

		}

	}

	@Test
	public void test() {
		NetworkConfigurationLoader loader = new NetworkConfigurationLoader();
		File file = new File(fileName);
		if (file.exists()) {
			assertEquals("Properties", properties, loader.load());
		}
		else {
			assertEquals("Properties", defaultProperties, loader.load());
		}

	}

}
