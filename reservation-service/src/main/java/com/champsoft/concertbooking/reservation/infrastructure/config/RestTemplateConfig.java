import org.spingframework.context.annotation.Bean;
import org.spingframework.context.annotation.Configuration;
import org.spingframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig{

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}