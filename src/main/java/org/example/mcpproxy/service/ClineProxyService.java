package org.example.mcpproxy.service;

import org.example.mcpproxy.config.McpProxyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ClineProxyService {

    @Autowired
    private McpProxyProperties mcpProxyProperties;

    @Autowired
    private RestTemplate restTemplate;


    private static final Logger log = LoggerFactory.getLogger(ClineProxyService.class);


}
