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
	private final String alpha = "3";
	private final String b = "160";
	private final String k = "20";
	private final String tExpire = "86400";
	private final String tRefresh = "3600";
	private final String tReplicate = "3600";
	private final String tRepublish = "86400";

	public NetworkConfiguration load() {
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
		return new NetworkConfiguration(prop.getProperty("alpha", alpha), prop.getProperty("B", b), prop.getProperty(
				"k", k), prop.getProperty("tExpire", tExpire), prop.getProperty("tRefresh", tRefresh),
				prop.getProperty("tReplicate", tReplicate), prop.getProperty("tRepublish", tRepublish));
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
