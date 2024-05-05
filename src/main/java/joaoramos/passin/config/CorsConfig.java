package joaoramos.passin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplicar configuração CORS para todos endpoints
                .allowedOrigins("*") // Escolher as origens de requisição permitidas
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Escolher quais métodos HTTP são permitidos
                .allowedHeaders("*"); // Escolher headers permitidos
    }
}
