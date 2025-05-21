package com.msl.mcp.control;

import com.msl.mcp.services.DateTimeTools;
import com.msl.mcp.services.WeatherService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author adley
 * @des
 */
@Slf4j
@RestController
@Data
public class ChatToolsController {

    private final OllamaChatModel chatModel;


    @Autowired
    WeatherService weatherTools;

    @Autowired
    public ChatToolsController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/toolcall")
    public Map<String,String> toolcall(@RequestParam(value = "message", defaultValue = "请告诉我当前时间") String message) {
        String response = ChatClient.create(chatModel)
                .prompt(message)
                .tools(new DateTimeTools())
                .call()
                .content();
        Map<String, String> generation = Map.of("小明", response);
        log.info("小明: {}", generation);
        return generation;
    }


    @GetMapping("/ai/alarm")
    public Map<String,String> alarm(@RequestParam(value = "message", defaultValue = "你能在10分钟后设置闹钟吗？") String message) {
        String response = ChatClient.create(chatModel)
                .prompt(message)
                .tools(new DateTimeTools())
                .call()
                .content();
        Map<String, String> generation = Map.of("小明", response);
        log.info("小明: {}", generation);
        return generation;
    }

    @GetMapping("/ai/weater")
    public Map<String,String> toolweater(@RequestParam(value = "message", defaultValue = "天气预报") String message) {
        String response = ChatClient.create(chatModel)
                .prompt(message)
                .tools(weatherTools)
                .call()
                .content();
        Map<String, String> generation = Map.of("小明", response);
        log.info("小明: {}", generation);
        return generation;
    }
}
