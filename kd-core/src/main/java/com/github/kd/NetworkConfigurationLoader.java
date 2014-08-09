package com.github.kd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkConfigurationLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private NetworkConfiguration load() {
		Properties prop = new Properties();
		String fileName = "src/main/resources/kd.props";
		try {
			InputStream is = new FileInputStream(fileName);
			prop.load(is);

		} catch (FileNotFoundException e1) {
			logger.error("file src/main/resources/kd.props was not found, default network configuration parameters set!");
		} catch (IOException e) {
			logger.error("IOException occurs while reading src/main/resources/kd.props file, default network configuration parameters set!");
		}
		return new NetworkConfiguration(prop.getProperty("alpha", "3"), prop.getProperty("B", "160"), prop.getProperty(
				"k", "20"), prop.getProperty("tExpire", "86400"), prop.getProperty("tRefresh", "3600"),
				prop.getProperty("tReplicate", "3600"), prop.getProperty("tRepublish", "86400"));
	}
}

class NetworkConfiguration {
	private String alpha;
	private String B;
	private String k;
	private String tExpire;
	private String tRefresh;
	private String tReplicate;
	private String tRepublish;

	NetworkConfiguration(String alpha, String B, String k, String tExpire, String tRefresh, String tReplicate,
			String tRepublish) {
		this.alpha = alpha;
		this.B = B;
		this.k = k;
		this.tExpire = tExpire;
		this.tRefresh = tRefresh;
		this.tReplicate = tReplicate;
		this.tRepublish = tRepublish;
	}

	public String getAlpha() {
		return alpha;
	}

	public String getB() {
		return B;
	}

	public String getK() {
		return k;
	}

	public String gettExpire() {
		return tExpire;
	}

	public String gettRefresh() {
		return tRefresh;
	}

	public String gettReplicate() {
		return tReplicate;
	}

	public String gettRepublish() {
		return tRepublish;
	}

}
