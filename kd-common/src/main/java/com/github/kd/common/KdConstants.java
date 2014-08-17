package com.github.kd.common;

public final class KdConstants {

	public static final String NETWORK_DEGREE_PROPERTY = "kd.network.degree";

	public static final String KEY_SIZE_PROPERTY = "kd.key.size";

	public static final String BUCKET_SIZE_PROPERTY = "kd.bucket.size";

	public static final String EXPIRATION_TIME_PROPERTY = "kd.expire.time";

	public static final String REFRESH_TIME_PROPERTY = "kd.refresh.time";

	public static final String REPLICATION_TIME_PROPERTY = "kd.replicate.time";

	public static final String REPUBLICATION_TIME_PROPERTY = "kd.republish.time";

	private KdConstants() {
		throw new IllegalStateException("The constructor of the utils class musn't be called");
	}
}
