package com.msl.mcp;

import com.msl.mcp.server.AutocompleteProvider;
import com.msl.mcp.server.PromptProvider;
import com.msl.mcp.server.UserProfileResourceProvider;
import com.msl.mcp.services.DateTimeTools;
import com.msl.mcp.services.WeatherService;
import com.logaritex.mcp.spring.SpringAiMcpAnnotationProvider;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpApplication.class, args);
	}



	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService, DateTimeTools dateTimeTools) {
		return  MethodToolCallbackProvider.builder().toolObjects(weatherService,dateTimeTools).build();
	}

	@Bean
	public List<McpServerFeatures.SyncResourceSpecification> resourceSpecs(UserProfileResourceProvider userProfileResourceProvider) {
		return SpringAiMcpAnnotationProvider.createSyncResourceSpecifications(List.of(userProfileResourceProvider));
	}

	@Bean
	public List<McpServerFeatures.SyncPromptSpecification> promptSpecs(PromptProvider promptProvider) {
		return SpringAiMcpAnnotationProvider.createSyncPromptSpecifications(List.of(promptProvider));
	}

	@Bean
	public List<McpServerFeatures.SyncCompletionSpecification> completionSpecs(AutocompleteProvider autocompleteProvider) {
		return SpringAiMcpAnnotationProvider.createSyncCompleteSpecifications(List.of(autocompleteProvider));
	}





}
