package adria.pfa.adriaReporting.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateConfiguration {

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver loaderTemplateResolver = new ClassLoaderTemplateResolver();
        loaderTemplateResolver.setPrefix("templates/");
        loaderTemplateResolver.setSuffix(".html");
        loaderTemplateResolver.setTemplateMode("HTML5");
        loaderTemplateResolver.setCharacterEncoding("UTF-8");
        loaderTemplateResolver.setOrder(1);
        return loaderTemplateResolver;
    }
}
