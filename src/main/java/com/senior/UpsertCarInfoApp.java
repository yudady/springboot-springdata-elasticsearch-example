package com.senior;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by liyongguan on 2017/12/29.
 * 使用upsert操作，如果没有该条数据，先进行添加，如果存在，则进行修改
 */
public class UpsertCarInfoApp {
    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name","my-es")
                .put("client.transport.sniff",true)
                .build();
        TransportClient  client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new org.elasticsearch.common.transport.InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));

        XContentBuilder indexBuilder =XContentFactory.jsonBuilder().startObject()
                .field("brand","宝马")
                .field("name","bmw320")
                .field("price",3100000)
                .field("produce_date","2017-01-01")
                .endObject();
        IndexRequest indexRequest = new IndexRequest("car_shop","cars","1")
                .source(indexBuilder);

        XContentBuilder updateBulider = XContentFactory.jsonBuilder().startObject()
                .field("price",310000)
                .endObject();

        UpdateRequest updateRequest = new UpdateRequest("car_shop","cars","1")
                .doc(updateBulider).upsert(indexRequest);

        UpdateResponse updateResponse = client.update(updateRequest).get();
        System.out.println(updateResponse.getResult().getOp());
        client.close();
    }
}
