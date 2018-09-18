package br.com.itexto.mautic.endpoints;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;

import br.com.itexto.mautic.MauticClient;

public class BasicTest {
	
	public MauticClient getClient() {
		return new MauticClient(info("mautic.host"), info("mautic.username"), info("mautic.password"));
	}
	
	private Properties properties;
	
	private File getTestConfig() {
		return new File(System.getProperty("user.home") + "/itexto-mautic/test.properties");
	}
	
	public String info(String key) {
		return getProperties().getProperty(key);
	}
	
	public Properties getProperties() {
		
		if (this.properties == null) {
		Properties props = new Properties();
			try {
				props.load(new FileInputStream(getTestConfig()));
				this.properties = props;
			} catch (IOException ex) {
				throw new RuntimeException("Error reading test data");
			}
		}
		
		return this.properties;
	}
	
	@Before
	public void init() {
		File testConfig = getTestConfig();
		if (! testConfig.getParentFile().exists()) {
			testConfig.getParentFile().mkdirs();
		}
		if (! testConfig.exists()) {
			try (InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties");
				 FileOutputStream fos = new FileOutputStream(testConfig)) {
				byte[] buffer = new byte[8192];
				int c = -1;
				while ((c = input.read(buffer)) != -1) {
					fos.write(buffer, 0, c);
				}
			} catch (IOException ex) {
				throw new RuntimeException("Error starting test data");
			}
		}
		assertTrue(testConfig.exists());
		getProperties();
	}

}
