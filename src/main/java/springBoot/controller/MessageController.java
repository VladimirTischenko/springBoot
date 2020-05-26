package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springBoot.domain.Message;
import springBoot.domain.User;
import springBoot.repository.MessageRepository;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Vladimir on 18.05.2020.
 */
@Controller
@RequestMapping(MessageController.REST_URL)
public class MessageController {
    static final String REST_URL = "/messages";
    private final MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if(file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFilename = UUID.randomUUID().toString();
            String resultFilename = uuidFilename + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        message.setAuthor(user);
        messageRepository.save(message);
        return getMessages(model);
    }
}
