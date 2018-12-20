package tk.app.v1;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import tk.tommy.es.service.BookService;
import tk.tommy.es.model.Book;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@ComponentScan("tk.tommy.es")
public class Application implements CommandLineRunner {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private BookService bookService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        printElasticSearchInfo();

        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        Client client = elasticsearchTemplate.getClient();
        System.out.println(client.hashCode());
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        Book b1 = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
        for (int i = 0; i < 10; i++) {

            Book b = new Book("1003" + i, "Apache Solr Basics" + i, "Rambabu Posa" + i, "21-MAR-2017" + i);
            bookService.save(b);
            //System.out.println(b);
        }


        //fuzzey search
        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));
        System.out.println("bookService.findByAuthor : " + books.getSize());
        books.forEach(x -> System.out.println(x));

        System.out.println("--------------------------------");
        System.out.println("--------------------------------");

        bookService.delete(b1);
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");


        List<Book> bookss = bookService.findByTitle("Elasticsearch Basics");
        System.out.println("bookService.findByTitle : " + bookss.size());


        Book one = bookService.findOne("1003");
        System.out.println("findOne " + one);


        List<Book> bookss2 = bookService.findByTitle("Apache Solr Basics");
        System.out.println("bookService.findByTitle : " + bookss2.size());

        bookss.forEach(x -> System.out.println(x));

        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");


    }

    //useful for debug
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch-->");
        Client client = elasticsearchTemplate.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("<--ElasticSearch--");
    }

}