package com.msl.mcp;

import java.util.Map;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.CompleteRequest;
import io.modelcontextprotocol.spec.McpSchema.CompleteResult;
import io.modelcontextprotocol.spec.McpSchema.GetPromptRequest;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceRequest;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.ResourceReference;
import io.modelcontextprotocol.spec.McpSchema.PromptReference;

/**
 * @author Christian Tzolov
 */

public class SampleClient {

    private final McpClientTransport transport;

    public SampleClient(McpClientTransport transport) {
        this.transport = transport;
    }

    public void run() {

        var client = McpClient.sync(this.transport)
                .loggingConsumer(message -> {
                    System.out.println(">> Client Logging: " + message);
                })
                .build();

        client.initialize();

        client.ping();

        // List and demonstrate tools
        ListToolsResult toolsList = client.listTools();
        System.out.println("Available Tools = " + toolsList);
        toolsList.tools().stream().forEach(tool -> {
            System.out.println("Tool: " + tool.name() + ", description: " + tool.description() + ", schema: "
                    + tool.inputSchema());
        });

        CallToolResult weatherForcastResult = client.callTool(new CallToolRequest("getWeatherForecastByLocation",
                Map.of("latitude", "47.6062", "longitude", "-122.3321")));
        System.out.println("Weather Forcast: " + weatherForcastResult);


        // // Resources
        ReadResourceResult resource = client.readResource(new ReadResourceRequest("user-status://alice"));

        System.out.println("Resource = " + resource);

        // Prompts
        GetPromptResult prompt = client.getPrompt(
                new GetPromptRequest("personalized-message", Map.of("name", "Alice", "age", "14", "interests", "AI")));

        System.out.println("Prompt = " + prompt);

        // Completions
        CompleteResult completion = client.completeCompletion(new CompleteRequest(new ResourceReference("user-status://{username}"),
                new CompleteRequest.CompleteArgument("username", "a")));

        System.out.println("Completion = " + completion);

        CompleteResult completion2 = client.completeCompletion(new CompleteRequest(new PromptReference("personalized-message"),
                new CompleteRequest.CompleteArgument("name", "a")));

        System.out.println("Completion2 = " + completion2);

        client.closeGracefully();

    }

}