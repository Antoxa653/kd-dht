package com.github.kd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkConfigurationLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int alpha = 3;
	private int B = 160;
	private int k = 20;
	private int tExpire = 86400;
	private int tRefresh = 3600;
	private int tReplicate = 3600;
	private int tRepublish = 86400;

	public NetworkConfiguration load() {
		File props = new File("kd.props");
		if (props.exists()) {
			return readPropsFile();
		} else {
			return setDefaultNetConfiguration();
		}
	}

	private NetworkConfiguration readPropsFile() {
		try (FileReader reader = new FileReader("kd.props"); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				String propertieName = line.substring(0, line.indexOf("=")).trim();
				int propertieValue = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.length()).trim());
				switch (propertieName) {
				case "alpha":
					alpha = propertieValue;
					break;
				case "B":
					B = propertieValue;
					break;
				case "k":
					k = propertieValue;
					break;
				case "tExpire":
					tExpire = propertieValue;
					break;
				case "tRefresh":
					tRefresh = propertieValue;
					break;
				case "tReplicate":
					tReplicate = propertieValue;
					break;
				case "tRepublish":
					tRepublish = propertieValue;
					break;
				default:
					break;
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("kd.props file not found");
		} catch (IOException e) {
			logger.error("IOException occurs while reading kd.props file");
		}
		return new NetworkConfiguration(alpha, B, k, tExpire, tRefresh, tReplicate, tRepublish);
	}

	private NetworkConfiguration setDefaultNetConfiguration() {
		return new NetworkConfiguration(alpha, B, k, tExpire, tRefresh, tReplicate, tRepublish);
	}

}

class NetworkConfiguration {
	private int alpha;
	private int B;
	private int k;
	private int tExpire;
	private int tRefresh;
	private int tReplicate;
	private int tRepublish;

	NetworkConfiguration(int alpha, int B, int k, int tExpire, int tRefresh, int tReplicate, int tRepublish) {
		this.alpha = alpha;
		this.B = B;
		this.k = k;
		this.tExpire = tExpire;
		this.tRefresh = tRefresh;
		this.tReplicate = tReplicate;
		this.tRepublish = tRepublish;
	}

	public int getAlpha() {
		return alpha;
	}

	public int getB() {
		return B;
	}

	public int getK() {
		return k;
	}

	public int gettExpire() {
		return tExpire;
	}

	public int gettRefresh() {
		return tRefresh;
	}

	public int gettReplicate() {
		return tReplicate;
	}

	public int gettRepublish() {
		return tRepublish;
	}

}
