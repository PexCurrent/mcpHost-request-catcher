package org.example.mcpproxy.config;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mcp.proxy")
public class McpProxyProperties {
    // 配置真实模型地址，例如 https://api.openai.com/v1/chat/completions
    private String targetUrl; //
    // 目标模型需要 API Key
    private String apiKey;



    public String getTargetUrl() {
        return targetUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    @PostConstruct
    // 可以针对具体的操作系统环境，给出默认的日志路径
    private void init() {
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }



}
