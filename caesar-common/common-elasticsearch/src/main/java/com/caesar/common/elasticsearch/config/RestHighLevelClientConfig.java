package com.caesar.common.elasticsearch.config;

import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author caesar
 * @desc ElasticSearch HighLevelClient
 * @email 504479518@qq.com
 * @date 2021/12/1
 */
@ConfigurationProperties(prefix = "spring.elasticsearch.rest")
@Configuration
public class RestHighLevelClientConfig {

    @Setter
    private List<String> clusterNodes;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] hosts = clusterNodes.stream()
                .map(this::buildHttpHost) // eg: new HttpHost("127.0.0.1", 9200, "http")
                .toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(hosts));
    }

    private HttpHost buildHttpHost(String node) {
        String[] nodeInfo = node.split(":");
        return new HttpHost(nodeInfo[0].trim(), Integer.parseInt(nodeInfo[1].trim()), "http");
    }
}
