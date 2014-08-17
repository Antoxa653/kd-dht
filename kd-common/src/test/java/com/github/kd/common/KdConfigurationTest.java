package com.github.kd.common;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class KdConfigurationTest {

	@Test
	public void testPropertiesLoad() throws URISyntaxException {
		File configFile = new File("src/test/resources/kd.test.props");
		KdConfiguration kdConfig = KdConfiguration.load(configFile);

		Assert.assertNotNull(kdConfig);
		Assert.assertEquals(7, kdConfig.getValue(KdConstants.NETWORK_DEGREE_PROPERTY));
		Assert.assertEquals(120, kdConfig.getValue(KdConstants.KEY_SIZE_PROPERTY));
		Assert.assertEquals(25, kdConfig.getValue(KdConstants.BUCKET_SIZE_PROPERTY));
		Assert.assertEquals(86411, kdConfig.getValue(KdConstants.EXPIRATION_TIME_PROPERTY));
		Assert.assertEquals(3610, kdConfig.getValue(KdConstants.REFRESH_TIME_PROPERTY));
		Assert.assertEquals(3620, kdConfig.getValue(KdConstants.REPLICATION_TIME_PROPERTY));
		Assert.assertEquals(86401, kdConfig.getValue(KdConstants.REPUBLICATION_TIME_PROPERTY));
	}

	@Test
	public void testDefaultPropertiesLoad() {
		File configFile = new File("non-existing-file");
		KdConfiguration kdConfig = KdConfiguration.load(configFile);

		Assert.assertNotNull(kdConfig);
		Assert.assertEquals(3, kdConfig.getValue(KdConstants.NETWORK_DEGREE_PROPERTY));
		Assert.assertEquals(20, kdConfig.getValue(KdConstants.KEY_SIZE_PROPERTY));
		Assert.assertEquals(20, kdConfig.getValue(KdConstants.BUCKET_SIZE_PROPERTY));
		Assert.assertEquals(86410, kdConfig.getValue(KdConstants.EXPIRATION_TIME_PROPERTY));
		Assert.assertEquals(3600, kdConfig.getValue(KdConstants.REFRESH_TIME_PROPERTY));
		Assert.assertEquals(3600, kdConfig.getValue(KdConstants.REPLICATION_TIME_PROPERTY));
		Assert.assertEquals(86400, kdConfig.getValue(KdConstants.REPUBLICATION_TIME_PROPERTY));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonExistingProperty() {
		KdConfiguration kdConfig = KdConfiguration.load(null);
		kdConfig.getValue("non-existing-property-key");
	}
}
