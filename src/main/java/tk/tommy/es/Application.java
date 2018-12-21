package tk.tommy.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScan("tk.tommy.es")
@EnableElasticsearchRepositories(basePackages = "tk.tommy.es.repository")


public class Application {


    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);

    }


}