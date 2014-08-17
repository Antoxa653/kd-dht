package com.github.kd.common;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class KdConfigurationTest {

	@Test
	public void testPropertiesLoad() throws URISyntaxException {
		File propertiesFile = new File("src/test/resources/kd.test.props");
		KdProperties kdProperties = KdProperties.load(propertiesFile);

		Assert.assertNotNull(kdProperties);
		Assert.assertEquals(7, kdProperties.getValue(KdConstants.NETWORK_DEGREE_PROPERTY));
		Assert.assertEquals(120, kdProperties.getValue(KdConstants.KEY_SIZE_PROPERTY));
		Assert.assertEquals(25, kdProperties.getValue(KdConstants.BUCKET_SIZE_PROPERTY));
		Assert.assertEquals(86411, kdProperties.getValue(KdConstants.EXPIRATION_TIME_PROPERTY));
		Assert.assertEquals(3610, kdProperties.getValue(KdConstants.REFRESH_TIME_PROPERTY));
		Assert.assertEquals(3620, kdProperties.getValue(KdConstants.REPLICATION_TIME_PROPERTY));
		Assert.assertEquals(86401, kdProperties.getValue(KdConstants.REPUBLICATION_TIME_PROPERTY));
	}

	@Test
	public void testDefaultPropertiesLoad() {
		File propertiesFile = new File("non-existing-file");
		KdProperties kdProperties = KdProperties.load(propertiesFile);

		Assert.assertNotNull(kdProperties);
		Assert.assertEquals(3, kdProperties.getValue(KdConstants.NETWORK_DEGREE_PROPERTY));
		Assert.assertEquals(20, kdProperties.getValue(KdConstants.KEY_SIZE_PROPERTY));
		Assert.assertEquals(20, kdProperties.getValue(KdConstants.BUCKET_SIZE_PROPERTY));
		Assert.assertEquals(86410, kdProperties.getValue(KdConstants.EXPIRATION_TIME_PROPERTY));
		Assert.assertEquals(3600, kdProperties.getValue(KdConstants.REFRESH_TIME_PROPERTY));
		Assert.assertEquals(3600, kdProperties.getValue(KdConstants.REPLICATION_TIME_PROPERTY));
		Assert.assertEquals(86400, kdProperties.getValue(KdConstants.REPUBLICATION_TIME_PROPERTY));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonExistingProperty() {
		KdProperties kdProperties = KdProperties.load(null);
		kdProperties.getValue("non-existing-property-key");
	}
}
