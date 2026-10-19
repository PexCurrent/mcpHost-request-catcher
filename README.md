本项目使用的MCP Host是Cline，其他工具的配置应该是类似的
在 Cline 中配置 打开 Cline 设置，API Provider 选择 OpenAI Compatible。
Base URL 填你的 Java 应用地址 + /v1，例如：http://localhost:port/v1。
API Key 填模型服务商给的 Key（这个 Key 会被代理原样转发给上游）。
Model ID 填真实模型名称。 
Cline 会自动向 http://localhost:8080/v1/chat/completions 发送请求，
你的 Java 应用将其转发到 proxy.target-url 指向的真实地址，
所有交互内容会写入 logs/cline-proxy.log。