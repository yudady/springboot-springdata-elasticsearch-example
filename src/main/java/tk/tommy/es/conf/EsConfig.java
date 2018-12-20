package tk.tommy.es.conf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories(basePackages = "tk.tommy.es.repository")
public class EsConfig {
    private String EsHost = "192.168.79.50";

    private int EsPort = 9300;

    private String EsClusterName = "docker-cluster";

    @Bean
    public TransportClient transportClient() throws Exception {

        Settings esSettings = Settings.builder()
                .put("cluster.name", EsClusterName)
                .build();

        TransportClient client = new PreBuiltTransportClient(esSettings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), Integer.valueOf(EsPort)));
        return client;

    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(transportClient());
    }


}