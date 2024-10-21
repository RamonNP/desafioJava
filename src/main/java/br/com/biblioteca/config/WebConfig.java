package br.com.biblioteca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/projetos/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Configura o padrão de formato de datas para 'yyyy-MM-dd'
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(false); // Não usar formato ISO por padrão
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Define o padrão de data
        registrar.registerFormatters(registry); // Registra os formatadores no contexto Spring
    }
}
