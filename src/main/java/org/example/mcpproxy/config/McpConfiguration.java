package org.example.mcpproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class McpConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 连接超时（毫秒）
        factory.setConnectTimeout(5000);
        // 读取超时（毫秒），模型生成可能比较慢，建议设长一点，比如 60秒
        factory.setReadTimeout(60000);
        return new RestTemplate(factory); }
    }
