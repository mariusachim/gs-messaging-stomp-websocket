package com.example.messagingstompwebsocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@Controller
@AllArgsConstructor
public class GreetingController {

    private SimpMessagingTemplate template;

    @MessageMapping("/yooo")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @Scheduled(fixedDelay = 3000)
    public void tick() throws Exception {
        // broadcast works
        this.template.convertAndSend("/topic/greetings", "{\"content\": \"Hello there, calling back from server-side since command has been completed!\"}");

        // single 'callback' via queue + user does not work
        //this.template.convertAndSendToUser("userx", "/queue/greetings", "{\"content\": \"Hello there, calling back from server-side since command has been completed!\"}");
        log.info("ticked");
    }

}
