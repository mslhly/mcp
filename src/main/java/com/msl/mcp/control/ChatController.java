package com.msl.mcp.control;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author adley
 * @des
 */
@Slf4j
@RestController
@Data
public class ChatController {

    private final OllamaChatModel chatModel;

    @Autowired
    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }



    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "讲个笑话") String message) {
        ChatResponse response = this.getChatModel().call(new Prompt(message));
        Generation result = response.getResult();
        Map<String, String> generation = Map.of("小明", (result != null) ? result.getOutput().toString() : "");
        log.info("小明: {}", generation);
        return generation;
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "小明", defaultValue = "讲个冷笑话") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.getChatModel().stream(prompt);
    }

}
