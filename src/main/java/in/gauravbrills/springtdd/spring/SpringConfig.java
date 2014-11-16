package in.gauravbrills.springtdd.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@ComponentScan("in.gauravbrills.springtdd")
@PropertySource({ "classpath:app.properties" })
public class SpringConfig extends WebMvcConfigurerAdapter {
	/** The Constant DD_MM_YYYY. */
	private static final String DD_MM_YYYY = "yyyy-MM-dd";

	/** The Constant DATE_FORMAT. */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			DD_MM_YYYY);

	@Bean(name = "tilesViewResolver")
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}

	@Bean(name = "tilesConfigurer")
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("/WEB-INF/tiles.xml");
		configurer.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
		return configurer;
	}

	/**
	 * Properties.
	 * 
	 * @return the property sources placeholder configurer
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		return pspc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #configureMessageConverters(java.util.List)
	 */
	@Override
	public void configureMessageConverters(
			final List<HttpMessageConverter<?>> messageConverters) {
		final ClassLoader classLoader = getClass().getClassLoader();
		if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper",
				classLoader)) {
			MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jackson2HttpMessageConverter.getObjectMapper().disable(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			// Register date format for marshalling unmarshalling dates
			jackson2HttpMessageConverter.getObjectMapper().setDateFormat(
					DATE_FORMAT);
			// Json Pretty Formatting
			jackson2HttpMessageConverter.getObjectMapper().enable(
					SerializationFeature.INDENT_OUTPUT);
			messageConverters.add(jackson2HttpMessageConverter);
		}
		// configure byte array convertor for media
		messageConverters.add(new ByteArrayHttpMessageConverter());
		super.configureMessageConverters(messageConverters);
	}
}
