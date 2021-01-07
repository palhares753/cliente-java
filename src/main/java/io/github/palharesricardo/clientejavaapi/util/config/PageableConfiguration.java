package io.github.palharesricardo.clientejavaapi.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

/**
 * Class that implements the necessary settings to the pagination feature works correctly.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Configuration
public class PageableConfiguration {

	/**
	 * Method that allow customize Pageable configurations.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>PageableHandlerMethodArgumentResolverCustomizer</code> object
	 */
    @Bean
    PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
        return pageableResolver -> pageableResolver.setOneIndexedParameters(true);
    }
    
    /**
	 * Method that allow customize Sort configurations.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>SortHandlerMethodArgumentResolverCustomizer</code> object
	 */
    @Bean
    SortHandlerMethodArgumentResolverCustomizer sortResolverCustomizer() {
        return sortResolver -> sortResolver.setSortParameter("sort");
    }
}