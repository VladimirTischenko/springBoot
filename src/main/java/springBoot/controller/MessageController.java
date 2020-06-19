package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springBoot.domain.Message;
import springBoot.domain.User;
import springBoot.repository.MessageRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Vladimir on 18.05.2020.
 */
@RestController
@RequestMapping(MessageController.REST_URL)
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
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
    public Iterable<Message> getAllMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return messages;
    }

    @GetMapping("/{id}")
    public String getUserMessages(@PathVariable("id") User user, Map<String, Object> model) {
        Set<Message> messages = user.getMessages();
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping()
    public Iterable<Message> addNewMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(bindingResult);
            model.put("errors", errors);
        } else {
            if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
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
        }

        return getAllMessages(model);
    }

    @PostMapping("/{id}")
    public Message updateMessage(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Message message,
            @RequestBody Message updatedMessage
    ) {
        if (user.equals(message.getAuthor())) {
            String text = updatedMessage.getText();
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
                messageRepository.save(message);
            }
        }
        return message;
    }
}
