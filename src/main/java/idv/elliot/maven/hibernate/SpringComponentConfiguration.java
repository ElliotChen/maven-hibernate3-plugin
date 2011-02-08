package idv.elliot.maven.hibernate;

import javax.sql.DataSource;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.hibernate3.configuration.AbstractComponentConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
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
		DataSource dataSource = lsfb.getDataSource();
		ThreadLocalConnectionProvider.setDataSource(dataSource);

		Configuration configuration = lsfb.getConfiguration();
		configuration.setProperty(Environment.CONNECTION_PROVIDER, ThreadLocalConnectionProvider.class.getName());

		return configuration;
	}

	@Override
	protected void validateParameters() throws MojoExecutionException {
		String sessionFactoryBean = getExporterMojo().getComponentProperty("sessionFactoryBean", "sessionFactory");
		String contextLocations = getExporterMojo().getComponentProperty("contextLocations", "classpath*:spring*.xml, classpath*:application*.xml");
		logger.info("Initial info: [sessionFactoryBean]=" + sessionFactoryBean + ", [appContextLocations]="
				+ contextLocations);
		String[] locations = StringUtils.delimitedListToStringArray(contextLocations, ",");
		
		// Initial ApplicationContext from spring configuration xml files.
		this.applicationContext = new FileSystemXmlApplicationContext(locations);
		
		// Get AnnotationSessionFactoryBean from spring
		Object bean = applicationContext.getBean("&" + sessionFactoryBean);
		if (null == bean || !(bean instanceof LocalSessionFactoryBean)) {
			throw new MojoExecutionException("Please check your spring configuration. [sessionFactoryBean]="+ sessionFactoryBean);
		}
		LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean) bean;
	}

}
