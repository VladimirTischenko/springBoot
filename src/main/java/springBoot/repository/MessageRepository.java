package springBoot.repository;

import org.springframework.data.repository.CrudRepository;
import springBoot.domain.Message;

/**
 * Created by Vladimir on 18.05.2020.
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
