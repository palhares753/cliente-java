package io.github.palharesricardo.clientejavaapi.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class that implements the necessary settings for using Swagger as an API documentation tool.
 *  
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Configuration
@Profile({"dev"})
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Value("${release.version}")
	private String releaseVersion;
	
	@Value("${api.version}")
	private String apiVersion;
	
	/**
	 * Method that configure all the endpoint's mapped in the documentation.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 *  
	 * @return <code>Docket</code> object
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("io.github.palharesricardo.clientejavaapi.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	/**
	 * Method that configure the informations about the API. 
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>ApiInfo</code> object
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Client Java API")
				.description("Client Java API - Endpoint's documentation").version(releaseVersion.concat("_").concat(apiVersion))
				.build();
	}

}
