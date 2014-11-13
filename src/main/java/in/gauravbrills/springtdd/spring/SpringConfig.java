package in.gauravbrills.springtdd.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@ComponentScan("in.gauravbrills.springtdd")
public class SpringConfig {

	@Bean(name="tilesViewResolver")
	public UrlBasedViewResolver tilesViewResolver(){
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}
	
	@Bean(name="tilesConfigurer")
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("/WEB-INF/tiles.xml");
		configurer.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
		return configurer;
	}
}
