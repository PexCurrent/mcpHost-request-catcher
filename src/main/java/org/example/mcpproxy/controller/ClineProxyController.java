package org.example.mcpproxy.controller;

import org.example.mcpproxy.config.McpProxyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
public class ClineProxyController {

    private static final Logger log = LoggerFactory.getLogger(ClineProxyController.class);

    @Autowired
    private McpProxyProperties mcpProxyProperties;

    @Autowired
    private  RestTemplate restTemplate;



    @PostMapping("/v1/chat/completions")
    public ResponseEntity<String> proxyChatCompletions(
            @RequestBody(required = false) String requestBody,
            HttpServletRequest request) {

        // 1. 记录 Cline 发出的请求
        log.info("=== Cline Request ===");
        log.info("Headers: Authorization present? {}", request.getHeader("Authorization") != null);
        log.info("Body: {}", requestBody != null ? requestBody : "[empty]");

        // 2. 构造转发请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiKey = mcpProxyProperties.getApiKey();
        String targetUrl = mcpProxyProperties.getTargetUrl();
        // 优先使用 Cline 带来的 Authorization，如果没有则使用配置的 Key
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank()) {
            headers.set("Authorization", authHeader);
        } else if (apiKey != null && !apiKey.isBlank()) {
            headers.set("Authorization", "Bearer " + apiKey);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 3. 转发到真实模型端点
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 4. 记录模型返回的响应
            log.info("=== Model Response ===");
            log.info("Status: {}", response.getStatusCode());
            log.info("Body: {}", response.getBody());

            // 5. 把响应原样返回给 Cline
            return ResponseEntity.status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            log.error("Proxy request failed", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("{\"error\":\"Proxy error: " + e.getMessage() + "\"}");
        }
    }
}