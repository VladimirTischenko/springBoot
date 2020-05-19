package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springBoot.domain.Message;
import springBoot.repository.MessageRepository;

import java.util.Map;

/**
 * Created by Vladimir on 18.05.2020.
 */
@Controller
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping(path="/messages")
    public String getMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping(path="/messages")
    public String addNewMessage(Message message, Map<String, Object> model) {
        messageRepository.save(message);
        return getMessages(model);
    }
}
