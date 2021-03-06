package com.recruitment.assistant.documentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class houses all the swagger documentation configurations.
 *
 * @since 26-04-2019
 * @version 1.0
 * @author lalit
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${application.title}")
    private String title;

    @Value("${application.description}")
    private String description;

    @Value("${application.version}")
    private String version;

    @Value("${application.name}")
    private String name;

    @Value("${application.profileLink}")
    private String profileLink;

    @Value("${application.liscence}")
    private String liscence;

    @Value("${application.basePackage}")
    private String basePackage;

    @Value("${application.email}")
    private String email;

    private static final String BLANK = "";

    // To display important information related to the api on the swagger ui.
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(title,description, version,BLANK,new Contact(name, profileLink, email),
                liscence,BLANK);
    }
}
