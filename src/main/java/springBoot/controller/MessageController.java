package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.domain.Message;
import springBoot.domain.User;
import springBoot.repository.MessageRepository;

import java.util.Map;

/**
 * Created by Vladimir on 18.05.2020.
 */
@Controller
@RequestMapping(MessageController.REST_URL)
public class MessageController {
    static final String REST_URL = "/messages";
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping()
    public String getMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping()
    public String addNewMessage(
            @AuthenticationPrincipal User user,
            Message message,
            Map<String, Object> model
    ) {
        message.setAuthor(user);
        messageRepository.save(message);
        return getMessages(model);
    }
}
