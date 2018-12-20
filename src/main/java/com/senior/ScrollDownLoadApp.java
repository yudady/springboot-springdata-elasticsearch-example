package com.senior;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by liyongguan on 2017/12/29.
 * Scroll进行批量的下载
 */
public class ScrollDownLoadApp {
    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name","my-es")
                .put("client.transport.sniff",true)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));

        SearchResponse searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setScroll(new TimeValue(1000))
                .setQuery(QueryBuilders.termQuery("brand.keyword","宝马"))
                .setSize(1)
                .get();
        int batchCount = 0;
        do {
            for(SearchHit searchHit:searchResponse.getHits()){
                System.out.println("batchCount:"+ ++batchCount);
                System.out.println(searchHit.getSourceAsString());
                //TODO
                //进行其他的业务，此处是按批次进行的
            }
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(2000))
                    .execute()
                    .actionGet();
        }while (searchResponse.getHits().getHits().length!=0);

    }
}
