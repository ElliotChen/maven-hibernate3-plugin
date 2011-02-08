package idv.elliot.maven.hibernate;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.codehaus.mojo.hibernate3.ExporterMojo;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringComponentConfigurationTest {
	private static final Logger logger = LoggerFactory.getLogger(SpringComponentConfigurationTest.class);
	
	private static final Log log = new SystemStreamLog();
	@Test
	public void testAnnotationSessionFactoryBean() {
		SpringComponentConfiguration scc = new SpringComponentConfiguration();
		ExporterMojo mock = Mockito.mock(ExporterMojo.class);
		Mockito.when(mock.getComponentProperty("sessionFactoryBean", "sessionFactory")).thenReturn("sessionFactory");
		Mockito.when(mock.getComponentProperty("contextLocations", "classpath*:spring*.xml, classpath*:application*.xml")).thenReturn("idv/elliot/maven/hibernate/AnnotationHibernateContext.xml");
		
		scc.setExporterMojo(mock);
		
		try {
			scc.validateParameters();
		} catch (MojoExecutionException e) {
			logger.error("MojoExecutionException", e);
			Assert.fail();
		}
		scc.createConfiguration();
	}
	
	@Test
	public void testLocalSessionFactoryBean() {
		SpringComponentConfiguration scc = new SpringComponentConfiguration();
		ExporterMojo mock = Mockito.mock(ExporterMojo.class);
		Mockito.when(mock.getComponentProperty("sessionFactoryBean", "sessionFactory")).thenReturn("sessionFactory");
		Mockito.when(mock.getComponentProperty("contextLocations", "classpath*:spring*.xml, classpath*:application*.xml")).thenReturn("idv/elliot/maven/hibernate/HibernateContext.xml");
		
		scc.setExporterMojo(mock);
		
		try {
			scc.validateParameters();
		} catch (MojoExecutionException e) {
			logger.error("MojoExecutionException", e);
			Assert.fail();
		}
		scc.createConfiguration();
	}
}
