package tk.app.v2;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication
@ComponentScan("tk.tommy.es")
@EnableElasticsearchRepositories(basePackages = "tk.tommy.es.repository")
public class ApplicationV2 implements CommandLineRunner {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    TransportClient transportClient;


    public static void main(String args[]) {
        SpringApplication.run(ApplicationV2.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        Client client = elasticsearchTemplate.getClient();
        System.out.println(client.hashCode());
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        prepareData(transportClient);


        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        addEmploy(transportClient);
        undateEmployee(transportClient);
        delEmployee(transportClient);
        getEmployee(transportClient);


        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
    }

    /**
     * 添加数据
     *
     * @param client
     * @throws Exception
     */
    public static void prepareData(TransportClient client) throws Exception {
        XContentBuilder builder1 = XContentFactory.jsonBuilder().startObject()
                .field("name", "jack")
                .field("age", 27)
                .field("position", "technique software")
                .field("country", "China")
                .field("join_date", "2017-01-01")
                .field("salary", "10000")
                .endObject();
        client.prepareIndex("company", "employee", "1").setSource(builder1).get();

        XContentBuilder builder2 = XContentFactory.jsonBuilder().startObject()
                .field("name", "marry")
                .field("age", 35)
                .field("position", "technique manager")
                .field("country", "china")
                .field("join_date", "2017-01-01")
                .field("salary", 12000)
                .endObject();
        client.prepareIndex("company", "employee", "2").setSource(builder2).get();

        XContentBuilder builder3 = XContentFactory.jsonBuilder().startObject()
                .field("name", "tom")
                .field("age", 32)
                .field("position", "senior technique software")
                .field("country", "china")
                .field("join_date", "2016-01-01")
                .field("salary", 11000)
                .endObject();
        client.prepareIndex("company", "employee", "3").setSource(builder3).get();

        XContentBuilder builder4 = XContentFactory.jsonBuilder().startObject()
                .field("name", "jen")
                .field("age", 25)
                .field("position", "junior finance")
                .field("country", "usa")
                .field("join_date", "2016-01-01")
                .field("salary", 7000)
                .endObject();
        client.prepareIndex("company", "employee", "4").setSource(builder4).get();

        XContentBuilder builder5 = XContentFactory.jsonBuilder().startObject()
                .field("name", "mike")
                .field("age", 37)
                .field("position", "finance manager")
                .field("country", "usa")
                .field("join_date", "2015-01-01")
                .field("salary", 15000)
                .endObject();
        client.prepareIndex("company", "employee", "5").setSource(builder5).get();

    }

    /**
     * 搜索
     *
     * @param client
     */
    public static void executeSearch(TransportClient client) {

        MatchQueryBuilder builder = QueryBuilders.matchQuery("position", "technique");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").from(30).to(40);
        SearchResponse response = client.prepareSearch("company").setTypes("employee").setQuery(builder)
                .setPostFilter(rangeQueryBuilder)
                .setFrom(0).setSize(1).get();

        SearchHit[] hits = response.getHits().getHits();
        System.out.println(1234);
        for (int i = 0; i < hits.length; i++) {
            System.out.println(hits[i].getSourceAsString());
        }

    }


    public static void addEmploy(TransportClient client) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name", "zhangsan")
                .field("age", 27)
                .field("position", "technique english")
                .field("country", "China")
                .field("join_date", "2017-01-01")
                .field("salary", "10000")
                .endObject();
        IndexResponse response = client.prepareIndex("company", "employee", "6")
                .setSource(builder).get();
        System.out.println(response.getResult());
    }

    public static void delEmployee(TransportClient client) {
        DeleteResponse response = client.prepareDelete("company", "employee", "6").get();
        System.out.println(response.getResult());
    }

    public static void undateEmployee(TransportClient client) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name", "lisi").endObject();
        UpdateResponse response = client.prepareUpdate("company", "employee", "6")
                .setDoc(builder).get();
        System.out.println(response.getResult());
    }

    public static void getEmployee(TransportClient client) {
        GetResponse response = client.prepareGet("company", "employee", "6").get();
        System.out.println(response.getSourceAsString());
    }
}
