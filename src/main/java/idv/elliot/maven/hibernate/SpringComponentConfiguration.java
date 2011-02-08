package idv.elliot.maven.hibernate;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.hibernate3.ExporterMojo;
import org.codehaus.mojo.hibernate3.configuration.AbstractComponentConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.util.StringUtils;

public class SpringComponentConfiguration extends AbstractComponentConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(SpringComponentConfiguration.class);
	private ApplicationContext applicationContext = null;
	private LocalSessionFactoryBean lsfb = null;

	public String getName() {
		return "springconfiguration";
	}

	@Override
	protected Configuration createConfiguration() {
		ThreadLocalConnectionProvider.setDataSource(lsfb.getDataSource());
		Configuration configuration = lsfb.getConfiguration();
		configuration.setProperty(Environment.CONNECTION_PROVIDER, ThreadLocalConnectionProvider.class.getName());
		return configuration;
	}

	@Override
	protected void validateParameters() throws MojoExecutionException {
		String sessionFactoryBean = getExporterMojo().getComponentProperty("sessionFactoryBean", "sessionFactory");
		String contextLocations = getExporterMojo().getComponentProperty("contextLocations",
				"classpath*:spring*.xml, classpath*:*Context.xml");
		logger.info("Initial info: [sessionFactoryBean]=" + sessionFactoryBean + ", [contextLocations]="
				+ contextLocations);
		String[] locations = StringUtils.delimitedListToStringArray(contextLocations, ",");

		// Initial ApplicationContext from spring configuration xml files.
		try {

			this.applicationContext = new ClassPathXmlApplicationContext(locations);
		} catch (Exception e) {
			logger.error("Load Spring Configuration Fail.", e);
			throw new MojoExecutionException("Please check your spring configuration. [contextLocations]="
					+ contextLocations);
		}

		// Get SessionFactoryBean from spring
		Object bean = applicationContext.getBean("&" + sessionFactoryBean);
		if (null == bean || !(bean instanceof LocalSessionFactoryBean)) {
			logger.error("Can't find any bean named {} or {} is not a LocalSessionFactoryBean instance.",
					sessionFactoryBean, bean);
			throw new MojoExecutionException(
					"Please check your hibernate configurations for AnnotationSessionFactoryBean or LocalSessionFactoryBean. [sessionFactoryBean]="
							+ sessionFactoryBean);
		}

		this.lsfb = (LocalSessionFactoryBean) bean;
	}

}
