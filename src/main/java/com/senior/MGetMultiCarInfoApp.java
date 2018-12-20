package com.senior;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by liyongguan on 2017/12/29.
 * MGet一次性拿出多条数据
 */
public class MGetMultiCarInfoApp {
    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name","my-es")
                .put("client.transport.sniff",true)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("car_shop","cars","1")
                .add("car_shop","cars","2")
                .get();
        for(MultiGetItemResponse itemResponse : multiGetItemResponses){
            GetResponse response = itemResponse.getResponse();
            if(response.isExists()){
                System.out.println(response.getSourceAsString());
            }
        }
        client.close();
    }
}
