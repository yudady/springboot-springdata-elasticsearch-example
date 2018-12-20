package tk.tommy.es.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "blog",type = "blog")//elasticsearch的注解
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 4564729518133694581L;
    @Id
    private String id;
    private String title;
    private String summary;
    private String content;
}

