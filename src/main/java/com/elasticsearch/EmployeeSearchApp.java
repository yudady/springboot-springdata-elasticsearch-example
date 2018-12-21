package com.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by liyongguan on 2017/12/19.
 * 员工搜索应用app
 */
public class EmployeeSearchApp {
    public static void main(String[] args) throws  Exception{
        Settings seeting  = Settings.builder().put("cluster.name","elasticsearch").build();

        TransportClient client = new PreBuiltTransportClient(seeting)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.79.50"),9300));

//        prepareData(client);
        //executeSearch(client);
        client.close();
    }

    /**
     * 添加数据
     * @param client
     * @throws Exception
     */
    public static void prepareData(TransportClient client)throws  Exception{
        XContentBuilder builder1 = XContentFactory.jsonBuilder().startObject()
                .field("name","jack")
                .field("age",27)
                .field("position","technique software")
                .field("country","China")
                .field("join_date","2017-01-01")
                .field("salary","10000")
                .endObject();
        client.prepareIndex("company","employee","1").setSource(builder1).get();

        XContentBuilder builder2 = XContentFactory.jsonBuilder().startObject()
                .field("name", "marry")
                .field("age", 35)
                .field("position", "technique manager")
                .field("country", "china")
                .field("join_date", "2017-01-01")
                .field("salary", 12000)
                .endObject();
        client.prepareIndex("company","employee","2").setSource(builder2).get();

        XContentBuilder builder3 = XContentFactory.jsonBuilder().startObject()
                .field("name", "tom")
                .field("age", 32)
                .field("position", "senior technique software")
                .field("country", "china")
                .field("join_date", "2016-01-01")
                .field("salary", 11000)
                .endObject();
        client.prepareIndex("company","employee","3").setSource(builder3).get();

        XContentBuilder builder4 = XContentFactory.jsonBuilder().startObject()
                .field("name", "jen")
                .field("age", 25)
                .field("position", "junior finance")
                .field("country", "usa")
                .field("join_date", "2016-01-01")
                .field("salary", 7000)
                .endObject();
        client.prepareIndex("company","employee","4").setSource(builder4).get();

        XContentBuilder builder5 = XContentFactory.jsonBuilder().startObject()
                .field("name", "mike")
                .field("age", 37)
                .field("position", "finance manager")
                .field("country", "usa")
                .field("join_date", "2015-01-01")
                .field("salary", 15000)
                .endObject();
        client.prepareIndex("company","employee","5").setSource(builder5).get();

    }

    /**
     * 搜索
     * @param client
     */
    public static void executeSearch(TransportClient client){

        MatchQueryBuilder builder = QueryBuilders.matchQuery("position","technique");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").from(30).to(40);
        SearchResponse response =  client.prepareSearch("company").setTypes("employee").setQuery(builder)
                .setPostFilter(rangeQueryBuilder)
                .setFrom(0).setSize(1).get();

        SearchHit[] hits = response.getHits().getHits();
        System.out.println(1234);
        for(int i = 0;i<hits.length;i++){
            System.out.println(hits[i].getSourceAsString());
        }

    }
}
