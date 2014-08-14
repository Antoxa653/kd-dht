package com.github.kd;

public enum NetworkProperties {
	PARALLELISM_DEGREE("ParallelismDegree", "3"), KEY_SIZE("KeySize", "160"), CONTACTS_IN_THE_BUCKET(
			"ContactsInTheBucket", "20"), TEXPIRE("tExpire", "86400"), TREFRESH(
			"tRefresh", "3600"), TREPLICATE("tReplicate", "3600"), TREPUBLISH("tRepublish", "86400");
	private String key;
	private String value;

	private NetworkProperties(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
